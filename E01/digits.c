#include <stdio.h>
#include <string.h>

int main(void)
{
    int base, number;
    int return_code;
    char question;

    printf("enter question, base, and number to use \n");
    return_code = scanf(" %c%d%d", &question, &base, &number);
    printf("%c, %d, %d\n",question, base,number);
    return 0;
}