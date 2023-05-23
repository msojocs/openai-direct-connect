
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <sys/types.h> 
#include <sys/socket.h>
#include <arpa/inet.h>
#include <netinet/in.h>
#include <unistd.h>
#include <errno.h>

int main(int argc, char *argv[]){ 
    int sockfd;
    int len; 
    struct sockaddr_in address; 
    int result; 
    char httpstring[100]; 
    char host[] = "api.openai.com";
    char ip[] = "52.152.96.252";
    char buf[2056];

    sprintf(httpstring,"GET https://%s/v1/models HTTP/1.1\r\n"
          "Host: %s\r\n"
          "Connection: close\r\n\r\n", host, host);
    char ch;
    printf("prepare socket ...\n");

    sockfd = socket(AF_INET, SOCK_STREAM, 0); 
    address.sin_family = AF_INET;
    address.sin_addr.s_addr = inet_addr(ip); 
    address.sin_port = htons(80); 
    len = sizeof(address);

    printf("connect socket ...\n");
    result = connect(sockfd, (struct sockaddr *)&address, len); 
    printf("connect result: %d\n", result);
    if(result == -1){ 
        printf("connect error\n");
        perror("connect error\n"); 
        return 1; 
    }
    while (1)
    {
        printf("write socket data...\n");
        send(sockfd, httpstring, strlen(httpstring), 0);
        
        printf("read socket data...\n");
        sleep(1);

        int byte_count = recv(sockfd, buf, sizeof buf, 0);
        printf("recv()'d %d bytes of data in buf\n",byte_count);
        printf("%s\n",buf);
        
        printf("errno: %d\n", errno);
        if (errno != ECONNRESET) break;
        printf("retry...\n");
        sleep(1);
    }
    
    printf("close socket...\n");
    close(sockfd); 
    printf("end\n"); 
    return 0; 
 }