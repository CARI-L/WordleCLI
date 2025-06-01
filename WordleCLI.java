/*
A simple wordle game, played in a console
For testing wordle algorithm and logic
or for entertaining CariDev w/out ads

display scheme (ex. 'a'):
if letter is guessed, was in correct place: "A"
if letter is guessed, was in incorrect place: "a"
if letter is guessed, not found in word: "_";

previous guesses are displayed on the screen, with this feedback.
a bank of letters is shown, also displaying this information. unguessed letters are marked with an asterisk.

*/
//source: WordleCLI.Java
//author: CariDev

import java.util.ArrayList;
import java.io.File;
import java.io.FileNotFoundException;  
import java.util.Scanner; 


public class WordleCLI
{
    static ArrayList<String> words = new ArrayList<String>();
    
    // interface crap
    static String[] board = {"_ _ _ _ _", "_ _ _ _ _", "_ _ _ _ _", "_ _ _ _ _", "_ _ _ _ _", "_ _ _ _ _"};
    static char[] gameBC = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l','m', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'}; 
    static public String word;
    static char[] wordLets = new char[5];
    static char[] MAINABC = gameBC.clone();

    // Game Variables
    static String guess;
    static byte gCount = 0;

    public static void main(String[] args) 
    {
        // Game Loop Controls
        boolean running = true;
        
        // interface crap
        Scanner input = new Scanner(System.in);

        try 
        {
            File wordFile = new File("words.txt");
            Scanner reader = new Scanner(wordFile);
            while (reader.hasNextLine()) 
            {
                String data = reader.nextLine();
                words.add(data.substring(0, 5));
            }
            reader.close();
        } 
        catch (FileNotFoundException e) 
        {
            System.out.println("Umooooo!!! Couldnt find the file uwwwwwu. You seem to be missing your words.txt file. Uwa! Such confusion!");
            System.exit(1);
        }

        System.out.println("========================");
        System.out.println("|| Welcome To Wordle! ||");
        System.out.println("========================");
        System.out.println("Enter \"q\" to quit");
        
        newGame();
        while (running) 
        {
            printBoard();
            printBank();
            System.out.print("\nEnter a 5-Letter Word: ");
            guess = input.nextLine();

            if(guess.equals("q"))
            {
                System.out.printf("Answer this round was %s\n", word);
                System.out.println("Thanks for Playing!");
                running=false;
            }
            else if(guess.equals("giasf")) System.out.println(word);
            else if(guess.equals(word))
            {
                System.out.printf("YOU WON!! Answer is: %s\n", word);
                newGame();
            }
            else if(guess.length() != 5 || !words.contains(guess)) System.out.println("Not a valid guess, please try again.");
            else update(guess);

            if(gCount > 6)
            {
                System.out.printf("Answer this round was %s\n", word);
                newGame();
            }
            
        }
        input.close();
        

    }

    static void printBoard() { for (String row : board) System.out.printf("%13s\n", row); }
    static void printBank() 
    {
        System.out.println();
        for(int i=0; i<gameBC.length;i++)
        {
            if((i%8 == 0 && i != 16 && i != 0 && i != 24) || i == 18) System.out.println(gameBC[i]);
            else if(i == 0) System.out.printf("%2c ", gameBC[i]);
            else if (i == 19) System.out.printf("%3c ", gameBC[i]);
            else System.out.print(gameBC[i] + " ");
        }
    }

    static void newGame() 
    {
        int randInd = randint(0, words.size()) ;
        word = words.get(randInd);
        word.getChars(0, 5, wordLets, 0);
        words.remove(randInd);
        for(int i = 0; i < 6; i++) board[i] = "_____";
        gameBC = MAINABC.clone();
        gCount = 0;

    }

    static int randint(int min, int max) { return ((int) Math.round(Math.random() * max)) + min; }

    static void update(String g)
    {
        char[] guessList = new char[5];
        g.getChars(0, 5, guessList, 0);

        for(int i = 0; i < 5; i++) 
        {
            boolean include = false;
            int bankIndex = getBankIndex(guessList[i]);
            if(guessList[i] == wordLets[i]) {
                guessList[i] = Character.toUpperCase(guessList[i]);
                gameBC[bankIndex] = Character.toUpperCase(gameBC[bankIndex]);
                continue;
            }
            for(char let: wordLets) if (guessList[i] == (let)) include = true;
            if(!include) {
                guessList[i]  = '_';
                gameBC[bankIndex] = '_';
            }
            
        }

        board[gCount] = new String(guessList);
        gCount++;
    }

    static int getBankIndex(char c) { return Character.getNumericValue(c) - 10; }
}