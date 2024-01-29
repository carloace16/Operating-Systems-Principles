public class JudgeThread extends Thread {
    public JudgeThread() {
        setName("Judge");
    }

    @Override
    public void run() {
        // Simulate judge activities
        msg("Announcing results and awards");

        // Simulate leaving after all athletes are gone
        msg("Leaving after all athletes are gone");
    }

    private void msg(String message) {
        long time = 0;
        System.out.println("[" + (System.currentTimeMillis()-time) + "] " + getName() + ": " + message);
    }

}
