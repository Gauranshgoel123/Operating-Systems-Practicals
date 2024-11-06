#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#define FN "data.txt"
#define IFN "index.txt"
#define HSZ 10

typedef struct { int id; char name[30]; } Rec;
typedef struct { int id; long pos; } Indx;

void seqW() {
    FILE *f = fopen(FN, "w"); Rec r;
    for (int i = 0; i < 10; i++) {
        printf("Enter ID and Name: ");
        scanf("%d %s", &r.id, r.name);
        fwrite(&r, sizeof(Rec), 1, f);
    }
    fclose(f);
}

void seqR() {
    FILE *f = fopen(FN, "r"); Rec r;
    while (fread(&r, sizeof(Rec), 1, f))
        printf("ID: %d, Name: %s\n", r.id, r.name);
    fclose(f);
}

void idxW() {
    FILE *f = fopen(FN, "w"), *ifp = fopen(IFN, "w"); Rec r; Indx ix; long p;
    for (int i = 0; i < 10; i++) {
        printf("Enter ID and Name: ");
        scanf("%d %s", &r.id, r.name);
        p = ftell(f);
        fwrite(&r, sizeof(Rec), 1, f);
        ix.id = r.id; ix.pos = p;
        fwrite(&ix, sizeof(Indx), 1, ifp);
    }
    fclose(f); fclose(ifp);
}

void idxR(int sid) {
    FILE *f = fopen(FN, "r"), *ifp = fopen(IFN, "r"); Indx ix; Rec r;
    while (fread(&ix, sizeof(Indx), 1, ifp)) {
        if (ix.id == sid) {
            fseek(f, ix.pos, SEEK_SET);
            fread(&r, sizeof(Rec), 1, f);
            printf("Found: ID: %d, Name: %s\n", r.id, r.name);
            fclose(f); fclose(ifp); return;
        }
    }
    printf("Not found.\n");
    fclose(f); fclose(ifp);
}

int hash(int id) { return id % HSZ; }

void hashW() {
    FILE *ht[HSZ]; Rec r; char fn[20];
    for (int i = 0; i < HSZ; i++) {
        sprintf(fn, "hash%d.txt", i);
        ht[i] = fopen(fn, "w");
    }
    for (int i = 0; i < 10; i++) {
        printf("Enter ID and Name: ");
        scanf("%d %s", &r.id, r.name);
        int h = hash(r.id);
        fwrite(&r, sizeof(Rec), 1, ht[h]);
    }
    for (int i = 0; i < HSZ; i++) fclose(ht[i]);
}

void hashR(int sid) {
    FILE *f; Rec r; char fn[20];
    sprintf(fn, "hash%d.txt", hash(sid));
    f = fopen(fn, "r");
    while (fread(&r, sizeof(Rec), 1, f)) {
        if (r.id == sid) {
            printf("Found: ID: %d, Name: %s\n", r.id, r.name);
            fclose(f); return;
        }
    }
    printf("Not found.\n");
    fclose(f);
}

int main() {
    int ch, sid;
    printf("1.Seq Write 2.Seq Read 3.Idx Write 4.Idx Read 5.Hash Write 6.Hash Read 7.Exit\n");
    while (1) {
        printf("Choice: ");
        scanf("%d", &ch);
        if (ch == 7) exit(0);
        switch (ch) {
            case 1: seqW(); break;
            case 2: seqR(); break;
            case 3: idxW(); break;
            case 4: printf("ID: "); scanf("%d", &sid); idxR(sid); break;
            case 5: hashW(); break;
            case 6: printf("ID: "); scanf("%d", &sid); hashR(sid); break;
            default: printf("Invalid.\n");
        }
    }
    return 0;
}
convertto java
