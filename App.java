import java.util.Scanner;
public class App {
    public static void main(String[] args) throws Exception {
        for(int i=0; i < 10; i++)
        {
            System.out.println("");
        }
        System.out.println("|                                              |");
        System.out.println("|             Welcome To Nim Game!             |");
        System.out.println("|                                              |");
        Game runner = new Game();
        Scanner scan = new Scanner(System.in);
        System.out.println(" Computer is going second. What is your move?");
        System.out.println(" Starting with:");
        System.out.println(runner);
        while(!runner.getGameEnd()){
            //player move:
            System.out.println("Insert data (row# and number of pieces to remove)");
            while(runner.getRunAgain()){
                runner.playerMove(scan.nextInt(), scan.nextInt());
            }
            runner.setRunAgain(true);
            // -------- end of player move ----------
            System.out.println("Current pieces remaining: ");
            System.out.println(runner);
            if(runner.getGameEnd()){
                break;
            }
            System.out.println("爪ㄚ ㄒㄩ尺几 几ㄖ山...");
            runner.computerMove();
            System.out.println("Current pieces remaining: ");
            System.out.println(runner);

        }
        if(runner.getComputerWins()){
            System.out.println("|                                              |");
            System.out.println("|                    丨 山丨几...                |");
            System.out.println("|                                              |");
            System.out.println("|                     You Lose                 |");
            System.out.println("|                                              |");
        }
        else{
            System.out.println("|                                              |");
            System.out.println("|                   You Win!                   |");
            System.out.println("|                                              |");
        }


        scan.close();
    }
}

class Game {
    int rows = 4;
    boolean gameEnd = false;
    boolean computerWins = false;
    boolean runAgain = true;
    String[] gameStorage = {"001","011","101","111"};

    public boolean getComputerWins(){
        return computerWins;
    }

    public void setRunAgain(boolean input){
        runAgain = input;
    }

    public boolean getRunAgain(){
        return runAgain;
    }

    public boolean getGameEnd(){
        return gameEnd;
    }

    public String toString(){
        String rtn = "";
        for(int i = 0; i < 4; i++){
            rtn += "Row: " + (i + 1) + "     ";
            for(int w = 0; w < (4-i); w++){
                rtn += "  ";
            }
            for(int u = 0; u < Integer.parseInt(gameStorage[i],2); u++){
                rtn += " I";
            }
            rtn += "\n";
        }
        return rtn;//issue here
    }

    public void computerMove() {
        int tempTotal = 0;

        //adding rows together to check pairs
        for(int i = 0; i < 4; i++){
            tempTotal += Integer.parseInt(gameStorage[i]); //tempTotal = sum of all of the columns in this order: C3, C2, C1
        }

        boolean nimSum = true;

        //this should be changed later but works for now
        if(tempTotal == 0){
            gameEnd = true;
            computerWins = true;
        }

        int currentDel = 1;
        int currentRow = -1;
        String currentTotal = "";
        if(rows > 2){
            //works for first part:
            for (int i = 1; i <= 7; i++){
                currentDel = i;
                for (int u = 0; u < 4; u++){
                    tempTotal = 0;
                    nimSum = true;
                    currentRow = u;
                    if ((Integer.parseInt(gameStorage[currentRow],2) - currentDel) < 0){
                        currentTotal = "1111";
                    }
                    else{
                        currentTotal = Integer.toBinaryString(Integer.parseInt(gameStorage[currentRow],2) - currentDel);
                    }
                    if(Integer.parseInt(currentTotal) != 1111){
                        for(int w = 0; w < 4; w++){
                            if(w==currentRow){
                                tempTotal += Integer.parseInt(currentTotal);
                            }
                            else{
                                tempTotal += Integer.parseInt(gameStorage[w]); //tempTotal = sum of all of the columns in this order: C3, C2, C1
                            }
                        }
                        while(tempTotal>0){
                            if(tempTotal%2==1){
                                nimSum = false;
                            }
                            tempTotal = (int) Math.floor(tempTotal/10);
                        }
                    }
                    if (Integer.parseInt(currentTotal) != 1111 && nimSum){
                        break;
                    }
                }
                if (Integer.parseInt(currentTotal) != 1111 && nimSum){
                    break;
                }
            }
        } //WORKS BELOW
        else if(rows==1){//win case when rows <= 1
            for (int x = 0; x < 4; x++)
            {
                if(Integer.parseInt(gameStorage[x])!=0){
                    currentRow = x;
                } 
            }
            currentDel = Math.abs(1 - Integer.parseInt(gameStorage[currentRow],2));
            currentTotal = "1";
        } //Works ABOVE
        else{ //win case when rows <= 2
            // if one row has 1 piece:
            int otherRow = 0;
            for (int x = 0; x < 4; x++)
            {
                if(Integer.parseInt(gameStorage[x]) == 1){
                    currentRow = x;
                } 
            }
            if (currentRow >= 0){
                for (int x = 0; x < 4; x++)
                {
                    if(Integer.parseInt(gameStorage[x]) > 0 && x != currentRow){
                        otherRow = x;
                    } 
                }
                currentRow = otherRow;
                currentDel = Integer.parseInt(gameStorage[currentRow], 2);
                currentTotal = "0";
                rows--;
            }//end of 1 piece win case
            else{
                for (int x = 0; x < 4; x++)
                {
                    if(Integer.parseInt(gameStorage[x]) > 1){
                        currentRow = x;
                    } 
                }
                currentDel = Math.abs(2 - Integer.parseInt(gameStorage[currentRow],2));
                currentTotal = "10";
            }

            

        }
        if(Integer.parseInt(currentTotal) == 0){
            rows--;
        }
        gameStorage[currentRow] = currentTotal;
        System.out.println("丨 ㄒㄖㄖҜ " + currentDel + " 千尺ㄖ爪 尺ㄖ山 " + (currentRow + 1));

        //not sure if this is needed
        int tempTotalTwo = 0;
        for(int i = 0; i < 4; i++){
            tempTotalTwo += Integer.parseInt(gameStorage[i]);
        }
        if(tempTotalTwo==0){
            gameEnd = true;
            computerWins = false;
        }
        /*
        // ____This might need to be removed___
        //find which columns need fixing
        for(int i = 0; i < 3; i++){
            if(tempTotal%2==1){
                nimSum = false;
                column[i] = false;
            }
            tempTotal = (int) Math.floor(tempTotal/10);
        }
        //creates what is needed to be deleted:
        String numDelete = "";
        for(int i = column.length-1; i >= 0; i--){
            if(column[i]){
                numDelete += "0";
            }
            else{
                numDelete += "1";
            }
        }
        int rowDel = 0;
        for(int i = 0; i < gameStorage.length; i++){
            int test = Integer.parseInt(gameStorage[i]) - Integer.parseInt(numDelete);
            if((Integer.parseInt(gameStorage[i]) - Integer.parseInt(numDelete))>=0 && Integer.parseInt(Integer.toBinaryString(Integer.parseInt(gameStorage[i]) - Integer.parseInt(numDelete))) < Integer.parseInt(gameStorage[i])){
                rowDel=i;
            }
        }
        System.out.println("丨 ㄒㄖㄖҜ " + Integer.parseInt(numDelete,2) + " 千尺ㄖ爪 尺ㄖ山 " + (rowDel +1));
        gameStorage[rowDel] = Integer.toString(Integer.parseInt(gameStorage[rowDel])-Integer.parseInt(numDelete));
        int tempTotalTwo = 0;
        for(int i = 0; i < 4; i++){
            tempTotalTwo += Integer.parseInt(gameStorage[i]);
        }
        if(tempTotalTwo==0){
            gameEnd = true;
            computerWins = false;
        }*/
    }
//WORKS
    public void playerMove(int rowNum, int num){
        if (Integer.parseInt(gameStorage[rowNum-1],2)!=0){
            runAgain = false;
            int difference = (Integer.parseInt(gameStorage[rowNum-1],2) - num);
            if (difference < 0){
                difference = Integer.parseInt(gameStorage[rowNum-1],2);
                gameStorage[rowNum-1] = "000";
            }
            else{
                gameStorage[rowNum-1] = Integer.toBinaryString(Integer.parseInt(gameStorage[rowNum-1],2) - num);
                difference = num;
            }
            System.out.println("Removed " + difference + " from row " + rowNum);
            int total = 0;
            for(int i = 0; i < 4; i++){
                total += Integer.parseInt(gameStorage[i], 2);
            }
            if (total == 0){
                gameEnd = true;
                computerWins = true;
            }
            if (Integer.parseInt(gameStorage[rowNum-1],2) ==0){
                rows--;
            }
        }
        else{
            System.out.println("This row has nothing in it try again");
            runAgain = true;
        }
        //repairGameStorage();
    }

    /*private void repairGameStorage(){
        for(int i = 0; i < 4; i++){
            while(gameStorage[i].length() <3){
                gameStorage[i] = "0" + gameStorage[i];
            }
        }
    }*/
}

