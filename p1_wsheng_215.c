/* Winston Sheng G01306431
   CS262, Lab section 215
   Project 1
*/
#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <time.h>
#include <ctype.h>

int menu();
void addition(char *);
void race_status(int);
int is_valid_option(float);
void subtraction(char *);
void multiplication(char *);
void division(char *);


int main(){
    char buffer[100];
    int numChoice;

    srand(time(NULL));

    printf("\n*******************************\n*     Welcome to Math R@ce    *\n*******************************\n");
    printf("\nPlayer's name: \n");

    fgets(buffer, 98, stdin);

    while(strlen(buffer) > 36){

        printf("Name is too long, please re-enter: ");
        fgets(buffer, 98, stdin);
    }

    numChoice = menu();

    if(numChoice == 1){

        addition(buffer);

    }
    else if(numChoice == 2){

        subtraction(buffer);

    }
    else if(numChoice == 3){

        multiplication(buffer);

    }
    else if(numChoice == 4){

        division(buffer);

    }
    else if(numChoice == 5){

    }

    return 0;

}

int menu(){

    char menuChoice[100];
    char stringValid[100];

    int numChoiceint;
    float floatCheck;


    printf("\n1. Addition\n");
    printf("2. Subtraction\n");
    printf("3. Multiplication\n");
    printf("4. Division\n");
    printf("5. Quit\n");
    printf("\nSelect an option: ");

    
    fgets(menuChoice, 98, stdin);
    sscanf(menuChoice, "%d", &numChoiceint);   
    sscanf(menuChoice, "%f", &floatCheck); 
    
    while(!(is_valid_option(floatCheck) == 1)){
        sscanf(menuChoice, "%s", stringValid);

        printf("\nOption %s is not valid!\n", stringValid);
        printf("\nSelect an option: ");

        fgets(menuChoice, 5, stdin);
        
        sscanf(menuChoice, "%s", stringValid);   
        sscanf(menuChoice, "%f", &floatCheck);

        sscanf(menuChoice, "%d", &numChoiceint);

    }
    

    return numChoiceint;
}

int generateRandNum(int max, int min){
    
    int x;
    
    x = (rand() % (max - min + 1)) + min;

    return x;
}

void addition(char * name){

    char returnBuffer[50];
    char start[50];
    char ansBuffer[150];
    char ans;
    char correctAns;
    int questionNum;
    int numCorrect;

    int numChoice;

    int rp1;
    int rp2;

    int srp1;
    int srp2;

    int a;
    int f1;
    int f2;

    int choicePlacement;

    numCorrect = 0;
    questionNum = 0;

    rp1 = -1;
    rp2 = -1;
    srp1 = -1;
    srp2 = -1;

    f1 = 101;
    f2 = 101;

    name[strcspn(name, "\n")] = 0;

    printf("\n*******************************\n*     Math R@ce - Addition    *\n*******************************\n");
    race_status(0);
    printf("\n<Start>\n");
    fgets(start, 38, stdin);

    while(questionNum != 10){

        while((rp1 == srp1 && rp2 == srp2) || (rp1 == srp2 && rp2 == srp1) || rp1 + rp2 > 100){

            rp1 = generateRandNum(100, 0);
            rp2 = generateRandNum(100, 0);
            a = rp1 + rp2;

        }
        srp1 = rp1;
        srp2 = rp2;

        while(f1 > 100 || f2 > 100 || f1 == f2 || f1 == a || f2 == a){
         
            if(a - 10 >= 0 && a + 10 <= 100){
                f1 = generateRandNum(a + 10, a - 10);
                f2 = generateRandNum(a + 10, a - 10);
            }
            else if(a - 5 >= 0 && a + 10 <= 100){

                f1 = generateRandNum(a + 10, a - 5);
                f2 = generateRandNum(a + 10, a - 5);

            }
            else if(a - 10 >= 0 && a + 5 <= 100){

                f1 = generateRandNum(a + 5, a - 10);
                f2 = generateRandNum(a + 5, a - 10);
            }
            else if(a + 10 <= 100){

                f1 = generateRandNum(a + 10, a);
                f2 = generateRandNum(a + 10, a);

            }
            else if(a - 10 >= 0){

                f1 = generateRandNum(a, a-10);
                f2 = generateRandNum(a, a-10);

            }
        }
        
        choicePlacement = generateRandNum(3, 1);
        printf("\noperation %d:\n", questionNum + 1);
        printf("%d + %d\n", rp1, rp2);

        if(choicePlacement == 1){

            printf("a %d\n", a);
            printf("b %d\n", f1);
            printf("c %d\n", f2);

            correctAns = 'a';
        }
        else if(choicePlacement == 2){

            printf("a %d\n", f1);
            printf("b %d\n", a);
            printf("c %d\n", f2);
            
            correctAns = 'b';
        }
        else if(choicePlacement == 3){

            printf("a %d\n", f1);
            printf("b %d\n", f2);
            printf("c %d\n", a);

            correctAns = 'c';
        }

        printf("\nAnswer: ");
        fgets(ansBuffer, 10, stdin);
        sscanf(ansBuffer, "%c", &ans);

        while(!(ans == 'a' || ans == 'A' || ans == 'b' || ans == 'B' || ans == 'c' || ans == 'C')){

            printf("\nAnswer: ");
            fgets(ansBuffer, 10, stdin);
            sscanf(ansBuffer, "%c", &ans);

        }
        
        if(ans == correctAns || ans == correctAns - 32){

            printf("\nCorrect!\n");
            numCorrect++;

        }
        else{

            printf("\nIncorrect\n");

        }

        race_status(numCorrect);

        questionNum++;
        f1 = 101;
        f2 = 101;

    }
    if(numCorrect == 10){

            printf("\nCongratulations %s! You reached the finish line.\n", name);
            printf("\nPress <return> to go back to the menu");
            fgets(returnBuffer, 47, stdin);
            numChoice = menu();
            

        }
    else{

            printf("\nYou did not finish the race %s, you were missing %d steps to reach the finish line\n", name, 10-numCorrect);
            numChoice = menu();

        }
    
    if(numChoice == 1){

        addition(name);

    }
    else if(numChoice == 2){

        subtraction(name);

    }
    else if(numChoice == 3){

        multiplication(name);

    }
    else if(numChoice == 4){

        division(name);

    }
    else if(numChoice == 5){

    }
}

void subtraction(char * name){

    char returnBuffer[50];
        char start[50];
        char ansBuffer[150];
        char ans;
        char correctAns;
        int questionNum;
        int numCorrect;

        int numChoice;

        int rp1;
        int rp2;

        int srp1;
        int srp2;

        int a;
        int f1;
        int f2;

        int choicePlacement;

        numCorrect = 0;
        questionNum = 0;

        rp1 = -1;
        rp2 = -1;
        srp1 = -1;
        srp2 = -1;

        f1 = 101;
        f2 = 101;

        name[strcspn(name, "\n")] = 0;

        printf("\n*******************************\n*   Math R@ce - Subtraction   *\n*******************************\n");
        race_status(0);
        printf("\n<Start>\n");
        fgets(start, 38, stdin);

        while(questionNum != 10){

            while((rp1 == srp1 && rp2 == srp2) || (rp1 == srp2 && rp2 == srp1) || rp1 - rp2 < 0){

                rp1 = generateRandNum(100, 0);
                rp2 = generateRandNum(100, 0);
                a = rp1 - rp2;

            }
            srp1 = rp1;
            srp2 = rp2;

            while(f1 > 100 || f2 > 100 || f1 < 0 || f2 < 0 || f1 == f2 || f1 == a || f2 == a){
            
                if(a - 10 >= 0 && a + 10 <= 100){
                    f1 = generateRandNum(a + 10, a - 10);
                    f2 = generateRandNum(a + 10, a - 10);
                }
                else if(a - 5 >= 0 && a + 10 <= 100){

                    f1 = generateRandNum(a + 10, a - 5);
                    f2 = generateRandNum(a + 10, a - 5);

                }
                else if(a - 10 >= 0 && a + 5 <= 100){

                    f1 = generateRandNum(a + 5, a - 10);
                    f2 = generateRandNum(a + 5, a - 10);
                }
                else if(a + 10 <= 100){

                    f1 = generateRandNum(a + 10, a);
                    f2 = generateRandNum(a + 10, a);

                }
                else if(a - 10 >= 0){

                    f1 = generateRandNum(a, a-10);
                    f2 = generateRandNum(a, a-10);

                }
            }
            
            choicePlacement = generateRandNum(3, 1);
            printf("\noperation %d:\n", questionNum + 1);
            printf("%d - %d\n", rp1, rp2);

            if(choicePlacement == 1){

                printf("a %d\n", a);
                printf("b %d\n", f1);
                printf("c %d\n", f2);

                correctAns = 'a';
            }
            else if(choicePlacement == 2){

                printf("a %d\n", f1);
                printf("b %d\n", a);
                printf("c %d\n", f2);
                
                correctAns = 'b';
            }
            else if(choicePlacement == 3){

                printf("a %d\n", f1);
                printf("b %d\n", f2);
                printf("c %d\n", a);

                correctAns = 'c';
            }

            printf("\nAnswer: ");
            fgets(ansBuffer, 10, stdin);
            sscanf(ansBuffer, "%c", &ans);

            while(!(ans == 'a' || ans == 'A' || ans == 'b' || ans == 'B' || ans == 'c' || ans == 'C')){

                printf("\nAnswer: ");
                fgets(ansBuffer, 10, stdin);
                sscanf(ansBuffer, "%c", &ans);

            }
            
            if(ans == correctAns || ans == correctAns - 32){

                printf("\nCorrect!\n");
                numCorrect++;

            }
            else{

                printf("\nIncorrect\n");

            }

            race_status(numCorrect);

            questionNum++;
            f1 = 101;
            f2 = 101;

        }
        if(numCorrect == 10){

                printf("\nCongratulations %s! You reached the finish line.\n", name);
                printf("\nPress <return> to go back to the menu");
                fgets(returnBuffer, 47, stdin);
                numChoice = menu();

            }
        else{

                printf("\nYou did not finish the race %s, you were missing %d steps to reach the finish line\n", name, 10-numCorrect);
                numChoice = menu();

            }
        
        if(numChoice == 1){

        addition(name);

        }
        else if(numChoice == 2){

            subtraction(name);

        }
        else if(numChoice == 3){

            multiplication(name);

        }
        else if(numChoice == 4){

            division(name);

        }
        else if(numChoice == 5){

        }
}

void multiplication(char * name){

        char returnBuffer[50];
        char start[50];
        char ansBuffer[150];
        char ans;
        char correctAns;
        int questionNum;
        int numCorrect;

        int numChoice;

        int rp1;
        int rp2;

        int srp1;
        int srp2;

        int a;
        int f1;
        int f2;

        int choicePlacement;

        numCorrect = 0;
        questionNum = 0;

        rp1 = -1;
        rp2 = -1;
        srp1 = -1;
        srp2 = -1;

        f1 = 101;
        f2 = 101;

        name[strcspn(name, "\n")] = 0;

        printf("\n*******************************\n*  Math R@ce - Multiplication *\n*******************************\n");
        race_status(0);
        printf("\n<Start>\n");
        fgets(start, 38, stdin);

        while(questionNum != 10){

            while((rp1 == srp1 && rp2 == srp2) || (rp1 == srp2 && rp2 == srp1) || rp1 * rp2 > 100){

                rp1 = generateRandNum(100, 0);
                rp2 = generateRandNum(100, 0);
                a = rp1 * rp2;

            }
            srp1 = rp1;
            srp2 = rp2;

            while(f1 > 100 || f2 > 100 || f1 == f2 || f1 == a || f2 == a){
            
                if(a - 10 >= 0 && a + 10 <= 100){
                    f1 = generateRandNum(a + 10, a - 10);
                    f2 = generateRandNum(a + 10, a - 10);
                }
                else if(a - 5 >= 0 && a + 10 <= 100){

                    f1 = generateRandNum(a + 10, a - 5);
                    f2 = generateRandNum(a + 10, a - 5);

                }
                else if(a - 10 >= 0 && a + 5 <= 100){

                    f1 = generateRandNum(a + 5, a - 10);
                    f2 = generateRandNum(a + 5, a - 10);
                }
                else if(a + 10 <= 100){

                    f1 = generateRandNum(a + 10, a);
                    f2 = generateRandNum(a + 10, a);

                }
                else if(a - 10 >= 0){

                    f1 = generateRandNum(a, a-10);
                    f2 = generateRandNum(a, a-10);

                }
            }
            
            choicePlacement = generateRandNum(3, 1);
            printf("\noperation %d:\n", questionNum + 1);
            printf("%d * %d\n", rp1, rp2);

            if(choicePlacement == 1){

                printf("a %d\n", a);
                printf("b %d\n", f1);
                printf("c %d\n", f2);

                correctAns = 'a';
            }
            else if(choicePlacement == 2){

                printf("a %d\n", f1);
                printf("b %d\n", a);
                printf("c %d\n", f2);
                
                correctAns = 'b';
            }
            else if(choicePlacement == 3){

                printf("a %d\n", f1);
                printf("b %d\n", f2);
                printf("c %d\n", a);

                correctAns = 'c';
            }

            printf("\nAnswer: ");
            fgets(ansBuffer, 10, stdin);
            sscanf(ansBuffer, "%c", &ans);

            while(!(ans == 'a' || ans == 'A' || ans == 'b' || ans == 'B' || ans == 'c' || ans == 'C')){

                printf("\nAnswer: ");
                fgets(ansBuffer, 10, stdin);
                sscanf(ansBuffer, "%c", &ans);

            }
            
            if(ans == correctAns || ans == correctAns - 32){

                printf("\nCorrect!\n");
                numCorrect++;

            }
            else{

                printf("\nIncorrect\n");

            }

            race_status(numCorrect);

            questionNum++;
            f1 = 101;
            f2 = 101;

        }
        if(numCorrect == 10){

                printf("\nCongratulations %s! You reached the finish line.\n", name);
                printf("\nPress <return> to go back to the menu");
                fgets(returnBuffer, 47, stdin);
                numChoice = menu();

            }
        else{

                printf("\nYou did not finish the race %s, you were missing %d steps to reach the finish line\n", name, 10-numCorrect);
                numChoice = menu();

            }
        
        if(numChoice == 1){

            addition(name);

        }
        else if(numChoice == 2){

            subtraction(name);

        }
        else if(numChoice == 3){

            multiplication(name);

        }
        else if(numChoice == 4){

            division(name);

        }
        else if(numChoice == 5){

        }
}

void division(char * name){

        char returnBuffer[50];
        char start[50];
        char ansBuffer[150];
        char ans;
        char correctAns;
        int questionNum;
        int numCorrect;

        int numChoice;

        int rp1;
        int rp2;

        int srp1;
        int srp2;

        int a;
        int f1;
        int f2;

        int choicePlacement;

        numCorrect = 0;
        questionNum = 0;

        rp1 = -1;
        rp2 = -1;
        srp1 = -1;
        srp2 = -1;

        f1 = 101;
        f2 = 101;

        name[strcspn(name, "\n")] = 0;

        printf("\n*******************************\n*     Math R@ce - Division    *\n*******************************\n");
        race_status(0);
        printf("\n<Start>\n");
        fgets(start, 38, stdin);

        

        

        while(questionNum != 10){

            while((rp1 == srp1 && rp2 == srp2) || (rp1 == srp2 && rp2 == srp1) || rp2 == 0 || rp1 % rp2 != 0 || rp2 > rp1){

                rp1 = generateRandNum(100, 0);
                rp2 = generateRandNum(100, 0);
                if(rp2 == 0){}
                else{
                    a = rp1 / rp2;
                }
                
            }
            srp1 = rp1;
            srp2 = rp2;

            while(f1 > 100 || f2 > 100 || f1 == f2 || f1 == a || f2 == a){
            
                if(a - 10 >= 0 && a + 10 <= 100){
                    f1 = generateRandNum(a + 10, a - 10);
                    f2 = generateRandNum(a + 10, a - 10);
                }
                else if(a - 5 >= 0 && a + 10 <= 100){

                    f1 = generateRandNum(a + 10, a - 5);
                    f2 = generateRandNum(a + 10, a - 5);

                }
                else if(a - 10 >= 0 && a + 5 <= 100){

                    f1 = generateRandNum(a + 5, a - 10);
                    f2 = generateRandNum(a + 5, a - 10);
                }
                else if(a + 10 <= 100){

                    f1 = generateRandNum(a + 10, a);
                    f2 = generateRandNum(a + 10, a);

                }
                else if(a - 10 >= 0){

                    f1 = generateRandNum(a, a-10);
                    f2 = generateRandNum(a, a-10);

                }
            }
            
            choicePlacement = generateRandNum(3, 1);
            printf("\noperation %d:\n", questionNum + 1);
            printf("%d / %d\n", rp1, rp2);

            if(choicePlacement == 1){

                printf("a %d\n", a);
                printf("b %d\n", f1);
                printf("c %d\n", f2);

                correctAns = 'a';
            }
            else if(choicePlacement == 2){

                printf("a %d\n", f1);
                printf("b %d\n", a);
                printf("c %d\n", f2);
                
                correctAns = 'b';
            }
            else if(choicePlacement == 3){

                printf("a %d\n", f1);
                printf("b %d\n", f2);
                printf("c %d\n", a);

                correctAns = 'c';
            }

            printf("\nAnswer: ");
            fgets(ansBuffer, 10, stdin);
            sscanf(ansBuffer, "%c", &ans);

            while(!(ans == 'a' || ans == 'A' || ans == 'b' || ans == 'B' || ans == 'c' || ans == 'C')){

                printf("\nAnswer: ");
                fgets(ansBuffer, 60, stdin);
                sscanf(ansBuffer, "%c", &ans);

            }
            
            if(ans == correctAns || ans == correctAns - 32){

                printf("\nCorrect!\n");
                numCorrect++;

            }
            else{

                printf("\nIncorrect\n");

            }

            race_status(numCorrect);

            questionNum++;
            f1 = 101;
            f2 = 101;

        }
        if(numCorrect == 10){

                printf("\nCongratulations %s! You reached the finish line.\n", name);
                printf("\nPress <return> to go back to the menu");
                fgets(returnBuffer, 47, stdin);
                numChoice = menu();

            }
        else{

                printf("\nYou did not finish the race %s, you were missing %d steps to reach the finish line\n", name, 10-numCorrect);
                numChoice = menu();

            }
        
        if(numChoice == 1){

            addition(name);

        }
        else if(numChoice == 2){

            subtraction(name);

        }
        else if(numChoice == 3){

            multiplication(name);

        }
        else if(numChoice == 4){

            division(name);

        }
        else if(numChoice == 5){

        }
}

void race_status(int correct){

    int i;

    printf("\nRace Counter: %d/10\n", correct);
       
    for(i = 0; i < 10 ; i++){


        if(i == correct){

            printf("@ ");

        }
        
        else{

            printf("_ ");

        }
        
        if(i == 9){

            printf("%%\n");

        }
        

    }


}
int is_valid_option(float option){

    int intvers;

    intvers = option;

    if(option < 1 || option > 5){

        return 0;

    }
    else if(option - intvers != 0){

        return 0;

    }
    return 1;

}