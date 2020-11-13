package com;
import java.util.*;



public class Sorter {

    char[] ListToSort;
    long highTime = 0;
    long maxTime = 10000;
    static int runs = 5;
    int N = 1;

    char[] GenerateTestList(int N, int k, int minV, int maxV) {
        int len = this.N;
        char newList[] = new char[len];

        Random rand = new Random();
        int newValue;
        for (int z = 0; z < N; z++)
        {
            newValue = (rand.nextInt() % (maxV - minV)) + minV;
            newList[z] = (char) newValue;
        }

        return newList;
    }

    void InsertionSort() {
        int len = this.N;

        char MyList[] = new char[len];
        for (int z = 0; z < len; z++)
            MyList[z] = this.ListToSort[z];

        for (int z = 1; z < len; z++)
        {
            int key = MyList[z];
            int i = z - 1;

            while (i >= 0 && MyList[i] > key)
            {
                MyList[i + 1] = MyList[i];
                i--;
            }

            MyList[i + 1] = (char) key;
        }
    }

    void Merge(char MyList[], int l, int m, int r) {
        int n1 = m - l + 1;
        int n2 = r - m;

        char L[] = new char[n1], R[] = new char[n2];

        for (int z = 0; z < n1; z++)
            L[z] = MyList[z + l];
        for (int z = 0; z < n2; z++)
            R[z] = MyList[z + m + 1];

        int i = 0, j = 0, k = 1;
        while (i < n1 && j < n2)
        {
            if (L[i] <= R[j])
            {
                MyList[k] = L[i];
                i++;
            }

            else
            {
                MyList[k] = R[j];
                j++;
            }

            k++;
        }

        while (i < n1)
        {
            MyList[k] = L[i];
            i++;
            k++;
        }

        while (j < n2)
        {
            MyList[k] = R[j];
            j++;
            k++;
        }
    }

    void MergeSortHelper(char MyList[], int l, int r) {
        if (l < r)
        {
            int m = (l + r) / 2;

            this.MergeSortHelper(MyList, l, m);
            this.MergeSortHelper(MyList, m + 1, r);

            this.Merge(MyList, l, m, r);
        }
    }

    void MergeSort() {
        int len = this.N;

        char MyList[] = new char[len];
        for (int z = 0; z < len; z++)
            MyList[z] = this.ListToSort[z];

        this.MergeSortHelper(MyList, 0, len - 2);
    }

    int GetMax(char MyList[], int n) {
        int max = MyList[0];
        for (int z = 1; z < n; z++)
            if (MyList[z] > max)
                max = MyList[z];
        return max;
    }

    void CountSort(char MyList[], int n, int exp) {
        char TempList[] = new char[n];
        int i;
        int count[] = new int [10];
        Arrays.fill(count, 0);

        for (i = 0; i < n; i++)
            count[(MyList[i] / exp) % 10]++;

        for (i = 1; i < 10; i++)
            count[i] += count[i - 1];

        for (i = n - 1; i >= 0; i--)
        {
            TempList[count[(MyList[i] / exp) % 10] - 1] = MyList[i];
            count[(MyList[i] / exp) % 10]--;
        }
        for (i = 0; i < n; i++)
            MyList[i] = TempList[i];
    }

    void RadixSortHelper(char MyList[], int n) {
        int m = GetMax(MyList, n);
        for (int z = 1; m / z > 0; z *= 10)
            this.CountSort(MyList, n, z);
    }


    void RadixSort() {
        int len = this.N;
        char MyList[] = new char[len];
        for (int z = 0; z < len; z++)
            MyList[z] = this.ListToSort[z];

        this.RadixSortHelper(MyList, len);
    }

    public Sorter() {
        int k = 0, minV = 97, maxV = 122, runCounter = 1;
        long startTime, runTime, averageTime;

        System.out.println("PROGRAM START");
        while (highTime < maxTime)
        {
            this.ListToSort = this.GenerateTestList(this.N, k, minV, maxV);
            System.out.println("---");
            System.out.println("START RUN " + runCounter + ", N = " + this.N);

            // INSERTION SORT
            averageTime = 0;
            for (int z = 0; z < runs; z++)
            {
                startTime = System.currentTimeMillis();
                this.InsertionSort();
                runTime = System.currentTimeMillis() - startTime;
                if (runTime > highTime)
                    highTime = runTime;
                averageTime += (System.currentTimeMillis() - startTime);
            }
            System.out.println("iSORT: " + (averageTime / runs));

            // MERGE SORT
            averageTime = 0;
            for (int z = 0; z < runs; z++)
            {
                startTime = System.currentTimeMillis();
                this.MergeSort();
                runTime = System.currentTimeMillis() - startTime;
                if (runTime > highTime)
                    highTime = runTime;
                averageTime += (System.currentTimeMillis() - startTime);
            }
            System.out.println("mSORT: " + (averageTime / runs));

            // RADIX SORT
            averageTime = 0;
            for (int z = 0; z < runs; z++)
            {
                startTime = System.currentTimeMillis();
                this.RadixSort();
                runTime = System.currentTimeMillis() - startTime;
                if (runTime > highTime)
                    highTime = runTime;
                averageTime += (System.currentTimeMillis() - startTime);
            }
            System.out.println("rSORT: " + (averageTime / runs));

            this.N *= 2;
            runCounter++;
        }

        System.out.println("TIME EXCEEDED");
    }
}