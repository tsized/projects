int main(void) {
    pid_t pid = fork();

    if (pid > 0) {
    } else {
        pid = fork();
    }
    return 0;
}
