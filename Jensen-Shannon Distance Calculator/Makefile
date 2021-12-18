CC = gcc
CFLAGS = -g -std=c99 -Wvla -Wall -fsanitize=address,undefined -pthread

compare: compare.o link.o queue.o -lm
	$(CC) $(CFLAGS) -o $@ $^

compare.o: link.h queue.h
link.o: link.h
queue.o: queue.h

dlink.o: link.c link.h
	$(CC) -c $(CFLAGS) -DDEBUG -o $@ $<

dqueue.o: queue.c queue.h
	$(CC) -c $(CFLAGS) -DDEBUG -o $@ $<

dcompare: compare.o dlink.o dqueue.o
	$(CC) $(CFLAGS) -o $@ $^

clean:
	rm -f *.o compare
