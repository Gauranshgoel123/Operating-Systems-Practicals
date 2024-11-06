#include <iostream>
#include <thread>
#include <semaphore.h>

std::binary_semaphore empty(10), full(0);
int buffer[10], in = 0, out = 0;

void producer() {
    while (true) {
        empty.acquire();
        buffer[in] = rand() % 100;
        in = (in + 1) % 10;
        full.release();
    }
}

void consumer() {
    while (true) {
        full.acquire();
        std::cout << buffer[out] << std::endl;
        out = (out + 1) % 10;
        empty.release();
    }
}

int main() {
    std::thread p(producer), c(consumer);
    p.join(); c.join();
}
