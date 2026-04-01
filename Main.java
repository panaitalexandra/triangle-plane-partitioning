import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

public class Main {
    static float[] readPoint(BufferedReader br) throws IOException {
        int dimension = 2;
        float[] p = new float[dimension];
        String[] tokens = br.readLine().split(" ");
        for (int i = 0; i < dimension; i++)
            p[i] = Float.parseFloat(tokens[i]);
        return p;
    }

    static float determinantMatrix(float x1, float x2, float x3, float y1, float y2, float y3){
        return x1 * y2 + y1 * x3 + x2 * y3 - x3 * y2 - y3 * x1 - x2 * y1;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("Introduceti coordonatele punctului A");
        float[] A = readPoint(br);
        System.out.print("A(");
        for (int i = 0; i < 2; i++) {
            System.out.print(A[i]);
            if (i == 0)
                System.out.print(", ");
        }
        System.out.println(")");

        System.out.println("Introduceti coordonatele punctului B");
        float[] B = readPoint(br);
        System.out.print("B(");
        for (int i = 0; i < 2; i++) {
            System.out.print(B[i]);
            if (i == 0)
                System.out.print(", ");
        }
        System.out.println(")");

        System.out.println("Introduceti coordonatele punctului C");
        float[] C = readPoint(br);
        System.out.print("C(");
        for (int i = 0; i < 2; i++) {
            System.out.print(C[i]);
            if (i == 0)
                System.out.print(", ");
        }
        System.out.println(")");
        float detABC = determinantMatrix(A[0],B[0],C[0],A[1],B[1],C[1]);

        if(detABC == 0)
            System.out.println("Aceste trei puncte NU formeaza un triunghi, ci sunt COLINIARE");
        else
            System.out.println("Aceste trei puncte formeaza triunghiul ABC");

        Scanner in = new Scanner(System.in);
        while(true) {
            System.out.println("Introduceti coordonatele punctului M pentru a vedea daca apartine tringhiului");
            float[] M = readPoint(br);
            System.out.print("M(");
            for (int i = 0; i < 2; i++) {
                System.out.print(M[i]);
                if (i == 0)
                    System.out.print(", ");
            }
            System.out.println(")");

            float detMAB = determinantMatrix(M[0], A[0], B[0], M[1], A[1], B[1]);
            float detMBC = determinantMatrix(M[0], B[0], C[0], M[1], B[1], C[1]);
            float detMCA = determinantMatrix(M[0], C[0], A[0], M[1], C[1], A[1]);

            if (detMAB > 0 && detMBC > 0 && detMCA > 0)
                System.out.println("Se afla in interiorul triunghiului ABC");
            else {
                if (detMAB < 0 && detMBC > 0 && detMCA < 0)
                        System.out.println("Se afla in exteriorul triunghiului ABC - ZONA 2");
                    else
                    if(detMAB < 0 && detMBC < 0 && detMCA > 0)
                        System.out.println("Se afla in exteriorul triunghiului ABC - ZONA 3");
                    else
                    if(detMAB > 0 && detMBC < 0 && detMCA < 0)
                        System.out.println("Se afla in exteriorul triunghiului ABC - ZONA 4");
                    else
                    if(detMAB < 0 && detMBC > 0 && detMCA > 0)
                        System.out.println("Se afla in exteriorul triunghiului ABC - ZONA 5");
                    else
                    if(detMAB > 0 && detMBC > 0 && detMCA < 0)
                        System.out.println("Se afla in exteriorul triunghiului ABC - ZONA 6");
                    else
                    if(detMAB > 0 && detMBC < 0 && detMCA > 0)
                        System.out.println("Se afla in exteriorul triunghiului ABC - ZONA 7");
                    else
                    if(detMAB == 0 && detMBC > 0 && detMCA > 0)
                        System.out.println("Se afla pe FRONTIERA zonelor 1-5 a triunghiului ABC");
                    else
                    if(detMAB > 0 && detMBC > 0 && detMCA == 0)
                        System.out.println("Se afla pe FRONTIERA zonelor 1-6 a triunghiului ABC");
                    else
                    if(detMAB > 0 && detMBC == 0 && detMCA > 0)
                        System.out.println("Se afla pe FRONTIERA zonelor 1-7 a triunghiului ABC");
                }

            System.out.println("Continui? (da/nu)");
            String answer = in.next();

            if (answer.equalsIgnoreCase("nu")) {
                System.out.println("Program terminat");
                break;
            }
        }
    }
}
