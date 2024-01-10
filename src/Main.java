import java.lang.Thread;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;
public class Main {
    public static int stanTime = 2000;

    public static Random rand = new Random();
    public static int rainTime = 0;
    public static String[] timeArray = {"morning", "afternoon", "evening"};
    public static String currentTime = "afternoon";
    public static String[] weatherArray = {"rainfall", "sunshine"};
    public static int timeIndex = 0;

    public static int seekHelpTime = 0;

    public static Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) throws InterruptedException {
        levelOneIntro(); // Start of first Level
        while(true){
            char[] puzzleChar = new char[18];
            for (int i = 0; i<puzzleChar.length; i++) {
                int randomInt = rand.nextInt(65, 91);
                char Character = genChar(randomInt);
                puzzleChar[i] = Character;
            }
            int j = 1;
            int sum = 0;
            int position = 1;
            for (int i =0; i<16; i+=3){
                int distanceSum = (Math.abs(puzzleChar[i+1] - puzzleChar[i]) + Math.abs(puzzleChar[i+2] - puzzleChar[i+1]));
                if (sum <= distanceSum){
                    sum = distanceSum;
                    if ((i+1)%3 != 0){
                        position = ((i+1)/3)+1;
                    }
                    else{
                        position = (i+1)/3;
                    }
                }
                String cardCode = ""+puzzleChar[i] + puzzleChar[i+1] + puzzleChar[i+2];
                System.out.println("Card "+j+": "+cardCode);
                j++;
            }
            System.out.println("Do you want to \"seek for help\", or \"answer\" (please enter the exact words of the quotation for your choice: ");
            String userChoiceOne = scanner.nextLine();
            if (userChoiceOne.equals("seek for help")){
                seekHelpTime+=1;
                System.out.println("NPC: Here is a clue");
                Thread.sleep(stanTime);
                System.out.println("ACE is greater than OPR");
                System.out.println("and don't look at the letters separately, but as a whole");
                Thread.sleep(stanTime);
                System.out.println("Please answer which card is the biggest: ");
                int userAnsOne = scanner.nextInt();
                scanner.nextLine();
                if (userAnsOne != position) {
                    System.out.println("This is the wrong answer, do you want to restart? (Yes or No)");
                    String restartChoice = scanner.nextLine();
                    if (restartChoice.equals("No")) {
                        System.out.println("You lost the game...");
                        System.exit(0);
                    }
                }
                else{
                    break;
                }
            }
            else {
                System.out.println("Please enter which card is the biggest: ");
                int userAnsOne2 = scanner.nextInt();
                scanner.nextLine();
                if (userAnsOne2 == position){
                    break;
                }
                else{
                    System.out.println("This is the wrong answer, do you want to restart? (Yes or No):");
                    String restartChoice = scanner.nextLine();
                    if (restartChoice.equals("No")){
                        System.out.println("You lost the game...");
                        System.exit(0);
                    }
                }
            }
        }
        levelTwoIntro(); // Start of second level part 1
        String[] levelTwoAns = {"Water", "Salt", "Meat", "Vegetable", "Soy Sauce", "Sugar"};
        while(true){
            System.out.println("Do you want to \"harvest\" the vegetable in the farm or sit beside the \"campfire\"?");
            String userHarvestCamp = scanner.nextLine();
            if (userHarvestCamp.equals("harvest")){
                System.out.println("Do you want to harvest the vegetable right now? (Yes or No):");
                String userHarvest = scanner.nextLine();
                if (userHarvest.equals("Yes")) {
                    if (rainTime > 0 && currentTime.equals("morning")) {
                        System.out.println("You have successfully harvested the vegetable");
                        break;
                    } else {
                        System.out.println("You have not fulfilled the requirements to harvest the vegetables");
                    }
                }
            }
            else{
                campFire();
            }
        }
        levelTwoPartTwo(); // Start of second level part 2
        while(true) {
            System.out.println("Please enter the order in this format - ingredient 1, ingredient 2, ingredient 3: ");
            String ingredientAns = scanner.nextLine();
            String[] userAnsArray1 = ingredientAns.split(", ");
            if (!checkAns(userAnsArray1, levelTwoAns)) {
                System.out.println("This is the wrong answer, do you want to restart? (Yes or No):");
                String restartChoice = scanner.nextLine();
                if (restartChoice.equals("No")) {
                    System.out.println("You lost the game...");
                    System.exit(0);
                }
            }
            else{
                break;
            }
        }
        levelThreeIntro(); // Start of third level
        int[] standardAns = {squareRootFinder(81), squareRootFinder(56)};
        String[] standardAnsStr = new String[2];
        for (int i = 0; i<2; i++){
            standardAnsStr[i] = String.valueOf(standardAns[i]);
        }
        while(true){
            Scanner scanner = new Scanner(System.in);
            System.out.println(48+", "+squareRootFinder(48)+", "+79+", "+squareRootFinder(79)+", "+81+", ___, "+56+", ___, ");
            System.out.println("Please enter your answer in this format - ans1, ans2: ");
            String userAns = scanner.nextLine();
            String[] userAnsArray = userAns.split(", ");
            if (!checkAns(userAnsArray, standardAnsStr)) {
                System.out.println("This is the wrong answer, do you want to restart? (Yes or No):");
                String restartChoice = scanner.nextLine();
                if (restartChoice.equals("No")) {
                    System.out.println("You lost the game...");
                    System.exit(0);
                }
            }
            else{
                break;
            }
        }
        System.out.println("Surely, not all of this is luck.");
        if (seekHelpTime==0){
            System.out.println("Also, congratulations on beating the program without anyone’s help. You are a true cook of puzzles!");
        }
    }

    public static char genChar(int a){
        char intChar = (char) a;
        return intChar;
    }

    public static boolean checkAns(String[] userAns, String[] stanAns) {
        for (int i = 0; i<stanAns.length; i++){
            if (!userAns[i].equals(stanAns[i])){
                return false;
            }
        }
        return true;
    }


    public static void campFire(){
        int randomIndex = rand.nextInt(0, 2);
        String randomWeather = weatherArray[randomIndex];
        if (randomWeather.equals("rainfall")){
            rainTime+=1;
        }
        timeChange();
        System.out.println("After sitting beside the campfire, its "+currentTime+" and the weather is "+randomWeather);
    }

    public static void timeChange(){
        timeIndex = (timeIndex+1)%3;
        currentTime = timeArray[timeIndex];
    }

    public static void levelOneIntro()throws InterruptedException{
        System.out.println("The first stop of your train is at a casino..." +
                "You saw six cards on the desk placed downwards, each having three letters on the back." +
                "Behind the desk is your dealer, a well dressed gentleman");
        Thread.sleep(stanTime);
        System.out.println("The dealer: Welcome to your first game, its really nice to meet you" +
                "I prepared you with six different cards each with different combination" +
                "Your goal is to find the biggest card using the triplets of letter as a clues");
        Thread.sleep(stanTime);
        System.out.println("Oh, I just remembered. Don't mess it up, you only have one chance.");
        Thread.sleep(stanTime);
        System.out.println("Haha, just kidding... But you do no that you can't restart forever right?");
        Thread.sleep(stanTime);
        System.out.println("Here are the cards:");
    }

    public static void levelTwoIntro()throws InterruptedException{
        System.out.println("You are very lucky to pass the first test, and there will be a harder one. WAIT");
        Thread.sleep(stanTime);
        System.out.println("You entered through a village, the people there seem strange, as they do not have hands"+
                "You feel scared but they seem to do no harm" +
                "As you try to approach and introduce yourself, one of the monsters starts to walk towards you, with a spear on his hand" +
                "More and more start to surround you, hold your hand, and send you to their chief.");
        Thread.sleep(stanTime+1000);
        System.out.println("After 10 minutes of walking, you reached the chief's tent. The chief is tall, but has no hands" +
                "Chief: Traveler!! I finally found a traveler with hands. You must do us a favour"+
                "From his words, you find out that the hand-less tribe can't make culinary dishes on thier own," +
                "but since they saw the chefs on TV, they dreamed of eating a fine cooked dish");
        System.out.println("The dish requires 6 ingredients: Vegetable, Soy Sauce, Meat, Salt, Water, Sugar");
        Thread.sleep(stanTime);
        System.out.println("However, the tribe is very picky, so you must cook the dish in the correct order"+
                "What is more interesting about the chief is his obsession with poems. Therefore, he gave you clues for the correct order:");
        Thread.sleep(stanTime+5000);
        System.out.println("1. Ensure the pot echoes with the soothing whispers of the quenching life.");
        Thread.sleep(stanTime);
        System.out.println("2. As the pot cradles the essence, let the earth sprinkle its silent magic.");
        Thread.sleep(stanTime);
        System.out.println("3. Put the substance in,and let the symphony of sizzling begin.");
        Thread.sleep(stanTime);
        System.out.println("4. With all these ready, paint the soup with green in your palette");
        Thread.sleep(stanTime);
        System.out.println("5. In a sea of flavour, a wave of umami is always nice to add");
        Thread.sleep(stanTime);
        System.out.println("6. Finally, kiss and give a sense of sweetness to the dish");
        Thread.sleep(7000);
        System.out.println("Don't forget, the vegetables are in the farm. But wait till dawn's first light and after nature's tear to harvest it");
    }

    public static void levelTwoPartTwo()throws InterruptedException{
        System.out.println("Now you have all the ingredients, it is time to put them into the pot in the correct order. ");
        Thread.sleep(stanTime);
        System.out.println("""
                Let me remind you of the clues for the order:
                1. Ensure the pot echoes with the soothing whispers of the quenching life.
                2. As the pot cradles the essence, let the earth sprinkle its silent magic.
                3. Put the substance in,and let the symphony of sizzling begin.
                4. With all these ready, paint the soup with green in your palette
                5. In a sea of flavour, a wave of umami is always nice to add
                6. Finally, kiss and give a sense of sweetness to the dish
                """);
    }

    public static void levelThreeIntro()throws InterruptedException{
        System.out.println("Wow, you even passed the challenge from the notorious chief. You are a true talent. Now, for your last challenge, you have to do the following...");
        System.out.println("Oh wait, I haven't introduce myself yet. I am the creator of these worlds. I am here to give you your last challenge. Please listen.");
        Thread.sleep(stanTime);
        System.out.println("Here are how many numbers. The pattern lies between each consecutive pairs of numbers. For example, the 1st number and the 2nd are a pair, and the 3rd and the 4th are another pair.");
        Thread.sleep(stanTime);
        System.out.println("Here is the puzzle I give to you, please fill in the blanks:");
    }

    public static int squareRootFinder(int num){
        if (num == 0 || num == 1) {
            return num;
        }
        double low = 0;
        double high = num;
        double precision = 0.00001;

        while (true) {
            double mid = (low + high) / 2;
            double square = mid * mid;

            if (Math.abs(square - num) <= precision) {
                return (int)Math.ceil(mid);
            } else if (square < num) {
                low = mid;
            } else {
                high = mid;
            }
        }
    }

}
