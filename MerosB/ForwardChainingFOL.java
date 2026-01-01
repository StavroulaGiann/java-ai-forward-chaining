import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ForwardChainingFOL {

    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Format: java ForwardChainingFOL <knowledgeBase File> <query>");
            return;
        }

        String file = args[0];
        String query = args[1];

        try {
            List<String> clauses = readKB(file);
            Set<String> facts = new HashSet<>();
            Set<String> induced = new HashSet<>();
            List<String[]> rules = new ArrayList<>();

            /* Parse the facts and the rules from the file and add them to the proper lists. */
            for (String clause : clauses) {
                if (clause.contains("=>")) {
                    String[] parts = clause.split("=>");
                    String premises = parts[0].trim();
                    String conclusion = parts[1].trim();
                    rules.add(new String[]{premises, conclusion});
                } else {
                    facts.add(clause.trim());
                }
            }

            /* Implements Forward Chaining to determine if the query can be concluded and print the result */
            boolean result = forwardChaining(facts, induced, rules, query);
            System.out.println("Can we infer \"" + query + "\"? " + (result ? "YES" : "NO"));
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
    }

    /* Reads the knowledge database from the file line by line */
    public static List<String> readKB(String filePath) throws IOException {
        List<String> clauses = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    clauses.add(line.trim());
                }
            }
        }
        return clauses;
    }

    /* Forward Chaining implementation */
    public static boolean forwardChaining(Set<String> facts, Set<String> inferred, List<String[]> rules, String query) {
        boolean changed;

        do {
            changed = false;

            for (String[] rule : rules) {
                /* Separates the premises from the conclusion based on '^' symbol. */
                String[] premises = rule[0].split("\\^");
                String conclusion = rule[1].trim();

                /* Tries to unify the facts with the clauses. */
                for (Map<String, String> substitutions : findAllUnifications(premises, facts)) {
                    
                    /* Replaces the variables to derive the conclusion */
                    String unified = replacements(conclusion, substitutions);

                    /* Checks for contradiction with existing facts */
                    if (facts.contains("-" + unified) || facts.contains(unified.replaceFirst("^-", ""))) {
                        return false;
                    }

                    /* Add the induced conclusion if it does not already exists */
                    if (!facts.contains(unified) && !inferred.contains(unified)) {
                        facts.add(unified);
                        inferred.add(unified);
                        changed = true;

                        if (unified.equals(query)) {
                            return true;
                        }
                    }
                }
            }
        } while (changed);

        return facts.contains(query);
    }

    /* Returns the map containing all the successful unifications */
    public static List<Map<String, String>> findAllUnifications(String[] premises, Set<String> facts) {
        List<Map<String, String>> unifications = new ArrayList<>();

       /* Initialize an empty substitution map */
        prem_f_unification(premises, facts, 0, new HashMap<>(), unifications);

        return unifications;
    }


    /* Explores all possible combinations of facts and premises to find valid unifications, using recursion.  */
    private static void prem_f_unification(String[] premises, Set<String> facts, int index, Map<String, String> curr_Sub, List<Map<String, String>> unifications) {

        /*If the index is equal to the length of the premises array, all premises are unified. Add the substitutions to the list and return */
        if (index == premises.length) {
            unifications.add(new HashMap<>(curr_Sub));
            return;
        }

        /* Gets the premise in the 'index' position of the array */
        String premise = premises[index].trim();

        /*For each premise */
        for (String fact : facts) {

            /* Copies the current substitutions */
            Map<String, String> temp_sub = new HashMap<>(curr_Sub);

            /* Tries to unify the given premise with the given fact using the map with the copied substitutions */
            if (unify(premise, fact, temp_sub)) {
                prem_f_unification(premises, facts, index + 1, temp_sub, unifications);
            }
        }
    }


    /* Unifies clauses and facts  */
    public static boolean unify(String clause, String fact, Map<String, String> substitutions) {

        /* Checks if the clauses starts with specific characters */
        boolean not_clause = clause.startsWith("-");
        boolean not_fact = fact.startsWith("-");

        /* Removes symbol of negation from the clauses */
        if (not_clause) clause = clause.substring(1);
        if (not_fact) fact = fact.substring(1);

        /* Extracts the characters before the parentheses */
        String pred_clause = clause.substring(0, clause.indexOf('('));
        String pred_fact = fact.substring(0, fact.indexOf('('));

        /* If the number of the characters extracted from the clause are not equal to the number of the extracted facts, the unification cannot be done  */
        if (!pred_clause.equals(pred_fact)) {
            return false;
        }

        /* Puts the arguments of the clauses and the facts in a String array */
        String[] args_clause = clause.substring(clause.indexOf('(') + 1, clause.indexOf(')')).split(",");
        String[] args_fact = fact.substring(fact.indexOf('(') + 1, fact.indexOf(')')).split(",");


        /* If the number of arguments in the clause and fact are not equal, the unification cannot be done. */
        if (args_clause.length != args_fact.length) {
            return false;
        }

        /* Removes the spaces from the String arrays */
        for (int i = 0; i < args_clause.length; i++) {
            String argc = args_clause[i].trim();
            String argf = args_fact[i].trim();

            /* If argc is a variable */
            if (is_Var(argc)) {

                /* If a substitution for argc already exists in substitutions, it must match argf. */
                if (substitutions.containsKey(argc)) {
                    if (!substitutions.get(argc).equals(argf)) {
                        return false;
                    }
                }
                /* If not, put argc in the substitutions map */
                else {
                    substitutions.put(argc, argf);
                }
            }
            /* Otherwise, the unification cannot be done.   */
            else if (!argc.equals(argf)) {
                return false;
            }
        }

        /* Checks that both fact and clause are either negated or non-negated */
        return not_clause == not_fact;
    }

    /* Replaces the variables of the clauses and the facts with other terms  */
    public static String replacements(String clause, Map<String, String> substitutions) {
        for (Map.Entry<String, String> entry : substitutions.entrySet()) {
            clause = clause.replace(entry.getKey(), entry.getValue());
        }
        return clause;
    }

    /* Ensures that the argument is a variable by checking if the first character is lowercase */
    public static boolean is_Var(String term) {
        return Character.isLowerCase(term.charAt(0));
    }
}
