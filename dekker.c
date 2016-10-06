/*
			Autors
			
			Juanmi Forteza Fuster & Jaume Crespí Valero

			Explicació:

	Hem fet el primer intent de l'algorisme de Dekker, a cada procés només entrarà
	dins la secció crítica quan sigui el seu torn (turno). En cas de que no sigui 
	el seu torn, esperara dins un bucle fins que sigui el seu torn per 
	entrar dins la secció crítica. Quan un procés acaba la secció crítica asignarà
	el torn al següent procés.

	Amb aquesta implementació podem asegurar que es faran totes les operacions 
	pero tenim diversos desventatges:

	- Es que es pot produir
	espera infinita, a on un procés es queda infinitament esperant el seu torn.

	- Tampoc si un procés acaba dins la SC i vol tornar a entrar, aquest no podrà perque
	haurà d'esperar que l'altre procés entri i acabi la SC.

	- Sense suposicions de velocitat relativa. Perquè hem de suposar que anam alternant
	entre els dos procesos.

	- Es molt fàcil fer que falli. Fent que cada fil faci un nombre diferent d'operacions.

	- El temps d'execusió es molt lent degut a que cada procés fa polling esperant es seu torn.

*/


#include <pthread.h>
#include <stdio.h>
#include <stdlib.h>
#define NUM_THREADS 2
#define MAX_COUNT 100000000L

volatile long count = 0;
volatile int turno;
void *counter(void *threadid) {
	
	long tid, i, max = MAX_COUNT/NUM_THREADS;
	tid = (long)threadid;
	int otroHilo = (tid +1) % NUM_THREADS; // Si executa p és q i viceversa
	for (i=0; i < max; i++) {
		/* Dekker */

		while (turno == otroHilo);

		count++; //SC
		turno = otroHilo;
	}


	pthread_exit(NULL);
}

int main (int argc, char *argv[]) {
	pthread_t threads[NUM_THREADS];
	int rc;
	long t;
	turno = 0;
	for(t=0; t<NUM_THREADS; t++){
		rc = pthread_create(&threads[t], NULL, counter, (void *)t);
		// Si pthread_create va bé retorna 0 sino una valor que indica l'error
		if (rc){
			printf("ERROR; return code from pthread_create() is %d\n", rc);
			exit(-1);
		}
	}

	for(t=0; t<NUM_THREADS; t++){
		pthread_join(threads[t], NULL);
	}

	float error = (MAX_COUNT-count)/(float) MAX_COUNT *100;
	printf("Final result: %ld Expected: %ld Error: %3.3f%%\n", count, MAX_COUNT, error);
	puts("Bye from main");
	pthread_exit(NULL);
}
