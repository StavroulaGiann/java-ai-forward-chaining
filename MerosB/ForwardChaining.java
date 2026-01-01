import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class ForwardChaining {

    /* Class for clauses representation (format: premise -> conclusion) */
    public static class Clause {
        List<String> premise;
        String conclusion;

        public Clause(List<String> premise, String conclusion) {
            this.premise = premise;
            this.conclusion = conclusion;
        }
    }

    /* Returns true if the given query is proved from data in the knowledge base. Otherwise, it returns false. */
    public static boolean isEntailed(List<Clause> kb, String query) {
        /* Data structures initialization */
        Map<Clause, Integer> count = new HashMap<>();
        Queue<String> agenda = new LinkedList<>();
        Map<String, Boolean> facts = new HashMap<>(); // Stores both positive and negative facts
        Map<String, Set<Clause>> p_premise = new HashMap<>();

        /* Process each clause in the knowledge base */
        for (Clause clause : kb) {
            count.put(clause, clause.premise.size());

            /* Link premises to clauses */
            for (String premise : clause.premise) {
                if (!p_premise.containsKey(premise)) {
                    p_premise.put(premise, new HashSet<>());
                }
                p_premise.get(premise).add(clause);
            }

            /* Initialize facts map for the conclusion */
            if (!facts.containsKey(clause.conclusion)) {
                facts.put(clause.conclusion, null); // Null indicates unknown
            }
        }

        /* Add facts without premises to the agenda */
        for (Clause clause : kb) {
            if (clause.premise.isEmpty()) {
                agenda.add(clause.conclusion);
            }
        }

        /* Forward Chaining implementation */
        while (!agenda.isEmpty()) {
            String p = agenda.remove();
            boolean isNegative = p.startsWith("-");
            String normalizedFact = isNegative ? p.substring(1) : p;

            /* Check for contradictions */
            if (facts.containsKey(normalizedFact)) {
                Boolean currentState = facts.get(normalizedFact);
                if (currentState != null && currentState != !isNegative) {
                    System.out.println("Contradiction detected with fact: " + p);
                    return false;
                }
            }

            /* Add the new fact */
            facts.put(normalizedFact, !isNegative);

            /* If the query matches the current fact, return true */
            if (p.equals(query)) {
                return true;
            }

            /* Propagate the inference */
            if (p_premise.containsKey(p)) {
                for (Clause c : p_premise.get(p)) {
                    count.put(c, count.get(c) - 1);
                    if (count.get(c) == 0) {
                        String conclusion = c.conclusion;

                        /* Check for contradictions before adding to the agenda */
                        boolean conclusionNegated = conclusion.startsWith("-");
                        String normalizedConclusion = conclusionNegated ? conclusion.substring(1) : conclusion;

                        if (facts.containsKey(normalizedConclusion)) {
                            Boolean currentState = facts.get(normalizedConclusion);
                            if (currentState != null && currentState != !conclusionNegated) {
                                System.out.println("Contradiction detected with conclusion: " + conclusion);
                                return false;
                            }
                        }

                        /* Add the conclusion to the agenda */
                        agenda.add(conclusion);
                    }
                }
            }
        }

        return false;
    }

/* Reads and parses properly the data from the knowledge base */
public static List<Clause> readkb(String filename) throws IOException {
    List<Clause> kb = new ArrayList<>();
    try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
        String line;
        while ((line = br.readLine()) != null) {
            line = line.trim();
            if (line.isEmpty())
                continue;

            if (!line.contains("->")) {
                System.err.println("Invalid clause format (missing '->'): " + line);
                continue; // Skip invalid lines
            }

            String[] parts = line.split("->");
            if (parts.length != 2) {
                System.err.println("Invalid clause format (incorrect split): " + line);
                continue; // Skip invalid lines
            }

            String premisePart = parts[0].trim();
            String conclusion = parts[1].trim();

            List<String> premise = new ArrayList<>();
            if (!premisePart.isEmpty()) {
                premise = Arrays.asList(premisePart.split(","));
            }

            kb.add(new Clause(premise, conclusion));
        }
    }
    return kb;
}


    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println("Format: java ForwardChaining <knowledgeBaseFile> <query>");
            return;
        }

        String file = args[0];
        String query = args[1];

        try {
            /* Extract the knowledge base from the file */
            List<Clause> kb = readkb(file);

            /* Check if the query is entailed using the appropriate method. */
            boolean result = isEntailed(kb, query);
            System.out.println("Is \"" + query + "\" entailed? " + result);
        } catch (IOException e) {
            System.err.println("Error reading knowledge base file: " + e.getMessage());
        }
    }
}