
import java.util.Random;

public class AthleteThread extends Thread{
    private static final int DEFAULT_PRIORITY = 5;
    private static final int MAX_PRIORITY_INCREASE = 3;
    private static final char[] VALID_LETTERS = {'a', 'b', 'c', 'e'};
    private static final int MIN_FOREST_WORDS = 300;
    private static final int MAX_FOREST_WORDS = 600;

    private Random random = new Random();

    public AthleteThread(String team, int id) {
        setName(team + "-Athlete-" + id);
        setPriority(DEFAULT_PRIORITY);
    }

    @Override
    public void run() {
        // Forest obstacle
        forestObstacle();

        // Lake obstacle
        lakeObstacle();

        // Field obstacle
        fieldObstacle();
    }

    private void forestObstacle() {
        msg("Entering the Forest");
        int priorityIncrease = random.nextInt(MAX_PRIORITY_INCREASE) + 1;
        int currentPriority = getPriority();
        setPriority(currentPriority + priorityIncrease);

        int forestSize = random.nextInt(MAX_FOREST_WORDS - MIN_FOREST_WORDS) + MIN_FOREST_WORDS;
        char[] forest = generateRandomForest(forestSize);

        char[] magicWord = generateRandomMagicWord();

        boolean foundMap = false;
        for (char word : forest) {
            if (word == magicWord[0]) {
                // Simulate finding the map
                foundMap = true;
                break;
            }
        }

        if (!foundMap) {

            msg("Failed to find the map in the Forest, penalized by yielding");
        } else {
            msg("Found the map in the Forest");
        }

        // Reset priority
        setPriority(DEFAULT_PRIORITY);
        msg("Leaving the Forest");
    }

    private char[] generateRandomForest(int size) {
        char[] forest = new char[size];
        for (int i = 0; i < size; i++) {
            forest[i] = VALID_LETTERS[random.nextInt(VALID_LETTERS.length)];
        }
        return forest;
    }

    private char[] generateRandomMagicWord() {
        char[] magicWord = new char[4];
        for (int i = 0; i < 4; i++) {
            magicWord[i] = VALID_LETTERS[random.nextInt(VALID_LETTERS.length)];
        }
        return magicWord;
    }

    private void lakeObstacle() {
        // Simulate Lake obstacle
        msg("Line-up: " + getName());

        // Simulate resting
        long restTime = random.nextInt(5000) + 3000;
        msg("Resting for " + restTime + " milliseconds");
        try {
            sleep(restTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Line-up for the Lake obstacle
        msg("Line-up for Lake obstacle: R4, R5, R2, R1, R3, R7, R6");

        // Proceed one by one
        for (int i = 1; i <= 7; i++) {
            // Simulate crossing the lake forward
            msg("Crossing the Lake forward");
            long forwardTime = random.nextInt(3000) + 2000;
            try {
                sleep(forwardTime);
            } catch (InterruptedException e) {
                msg("Interrupted while crossing the Lake forward");
            }

            // Simulate crossing the lake back
            msg("Crossing the Lake back");
            long backTime = random.nextInt(3000) + 2000;
            try {
                sleep(backTime);
            } catch (InterruptedException e) {
                msg("Interrupted while crossing the Lake back");
            }

            // Interrupt the next athlete
            if (i < 7) {
                AthleteThread nextAthlete = findAthlete("R" + (i + 1));
                if (nextAthlete != null) {
                    nextAthlete.interrupt();
                    msg(getName() + " interrupted " + nextAthlete.getName());
                }
            }
        }
    }

    private AthleteThread findAthlete(String athleteName) {
        ThreadGroup currentGroup = Thread.currentThread().getThreadGroup();
        int numThreads = currentGroup.activeCount();
        Thread[] threads = new Thread[numThreads];
        currentGroup.enumerate(threads);

        for (Thread thread : threads) {
            if (thread instanceof AthleteThread && thread.getName().equals(athleteName)) {
                return (AthleteThread) thread;
            }
        }

        return null;
    }

    private void crossLake(String direction) {
        msg("Crossing the Lake " + direction);
        int sleepTime = random.nextInt(3000) + 1000;
        try {
            sleep(sleepTime);
        } catch (InterruptedException e) {
            msg("Interrupted while crossing the Lake " + direction);
        }
    }

    private void fieldObstacle() {
        msg("Resting and meeting in the cafeteria for breakfast");

        // Random sleep between 1000ms and 5000ms
        int sleepTime = random.nextInt(4000) + 1000;
        try {
            sleep(sleepTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Last athlete signals that they are all in for the next obstacle
        AthleteThread lastAthlete = getLastAthleteInTeam();
        if (lastAthlete != null) {
            lastAthlete.msg("Signaling all athletes are in for the next obstacle");
        }

        // Athletes busy-wait for the race to start
        while (!judgeIsReady()) {
            // Busy waiting
        }

        // Judge announces the start of the race
        msg("Race will start in 1/2 hours");

        // Athletes run and complete the race
        long raceStartTime = System.currentTimeMillis();
        msg("Running and completing the race");
        raceCompleted("Field");
        long raceEndTime = System.currentTimeMillis();
        long raceTime = raceEndTime - raceStartTime;
        msg("Completed the Field obstacle in " + raceTime + " milliseconds");
    }

    private AthleteThread getLastAthleteInTeam() {
        ThreadGroup currentGroup = Thread.currentThread().getThreadGroup();
        int numThreads = currentGroup.activeCount();
        Thread[] threads = new Thread[numThreads];
        currentGroup.enumerate(threads);

        AthleteThread lastAthlete = null;
        for (Thread thread : threads) {
            if (thread instanceof AthleteThread && thread.getName().startsWith(getName().substring(0, 1))) {
                lastAthlete = (AthleteThread) thread;
            }
        }

        return lastAthlete;
    }

    private boolean judgeIsReady() {
        // Simulate judge busy-waiting
        // Return true when the judge is ready
        return true;
    }

    private void raceCompleted(String obstacle) {
        // Simulate completing the race
        int sleepTime = random.nextInt(126) + 75;
        try {
            sleep(sleepTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        msg("Completed " + obstacle + " obstacle in " + sleepTime + " milliseconds");
    }

    private void msg(String message) {
        long time = 0;
        System.out.println("[" + (System.currentTimeMillis()-time) + "] " + getName() + ": " + message);
    }
    }

