#include <iostream>
#include <stdio.h>
#include <stdlib.h>
#include <cctype>
#include <algorithm>
using namespace std;

class sub_decryptor
{
    //variable declarations
private:
    char key[26];

public:
    //constructor
    sub_decryptor()
    {
        cout<<"\n Object Created. Starting decrytion"<<endl;
    }
    //reads the key in the char buffer
    int key_reader(FILE* fp)
    {
        int i =0;
        if(fp==NULL)
            return -1;
            //save the key into the key buffer
        while(!feof(fp))
        {
            if(i>25)
            return 0;
            key[i++]=fgetc(fp);
        }
        return 0;
    }
    //decrypts and appends to the output file handle provided
    int decrytor(FILE* in, FILE* out)
    {
        char temp;
        char *en = key+26;
       char *loc;
       int i = 0;
        if(in==NULL||out==NULL)
            return -1;
        fputc('\n',out);
        while(!feof(in))
        {
            temp = fgetc(in);
            if(temp==' '|| isdigit(temp))
                fputc(temp,out);
            else
            {
                loc = std::find(key,en,temp);
                i = loc - key;
                fputc('A'+i,out);
            }
        }
        return 0;
    }
     ~sub_decryptor()
    {
        free(key);
        cout<<"\n Object destroyed successfully"<<endl;
    }

};
int main()
{
    //file handles
    FILE *sub_key;
    FILE *plain_text;
    FILE *cypher;
    //instatiante a object
    //try opening files
    sub_decryptor d1;
    int result;
    sub_key = fopen("sub_key.txt","r");
    plain_text = fopen("sub_plaintext.txt","a");
    cypher = fopen("sub_ciphertext.txt","r");
    if(sub_key==NULL||plain_text==NULL||cypher==NULL)
    {
        cout<<"\nError reading files"<<endl;
        exit(1);
    }
   result = d1.key_reader(sub_key);
   fclose(sub_key);
   if(result==-1)
   {
    cout<<"\nError reading key file";
    exit(1);
   }
   result = d1.decrytor(cypher,plain_text);
   fclose(cypher);
   fclose(plain_text);
   if(result==-1)
   {
    cout<<"\nError reading/writing cipher or plain text file";
    exit(1);
   }
   cout<<"\nSuccessfully decrypted and appended data to the file";

    return 0;
}
