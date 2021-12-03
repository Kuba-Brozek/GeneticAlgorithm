package com.company;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Main {
    private static final ArrayList<Integer> ChromosomeList = new ArrayList<>();
    private static final ArrayList<Integer> ChromosomeList2 = new ArrayList<>();
    private static final ArrayList<String> ChromosomeListBinar = new ArrayList<>();
    private static final ArrayList<String> ChromosomeListBinar2 = new ArrayList<>();
    private static final ArrayList<Double> PercentageValueList = new ArrayList<>();
    private static final ArrayList<Integer> ValueList = new ArrayList<>();
    private static final ArrayList<Integer> FitList = new ArrayList<>();
    private static final ArrayList<PercentageRangeClass> PercentageRangeList = new ArrayList<>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Podaj liczbe chromosomów: ");
        int ChromosomesNumber = scanner.nextInt();
        System.out.println("Podaj współczynniki a,b,c,d funkcji ax^3 + bx^2 + cx + d " +
                "w kolejności: a ,b ,c ,d: ");
        int a = scanner.nextInt();
        int b = scanner.nextInt();
        int c = scanner.nextInt();
        int d = scanner.nextInt();
        System.out.println("Podaj Pk i Pm jako wartość od 0 do 1 w typie double: ");
        double Pk = scanner.nextDouble();
        double Pm = scanner.nextDouble();
        System.out.println("Podaj liczbę maksymalnych wystąpień wartości funkcji przystosowania");
        int NumOfMaxFitFunOccur = scanner.nextInt();
        int NumOfFitListIterations = 0;

        int NumOfIterations = 0;


        for ( int i=0; i<ChromosomesNumber; i++){
            int RandNumHelper = RandomNumberChromosome();
            ChromosomeList.add(i, RandNumHelper);
            ChromosomeList2.add(i, RandNumHelper);
            ValueList.add(CalcFun(a,b,c,d, ChromosomeList.get(i)));
        }
        System.out.println(" | Początkowa pula chromosomów: " + ChromosomeList + " | ");

        FitList.add(0,0);

        for(int i = 0; i < ChromosomesNumber; i++){
            String BinarValue = Integer.toBinaryString(ChromosomeList.get(i));
            if (BinarValue.length() == 4){
                BinarValue = "0" + BinarValue;
            }
            else if(BinarValue.length() == 3){
                BinarValue = "0" + "0" + BinarValue;
            }
            else if (BinarValue.length() == 2){
                BinarValue = "0" + "0" + "0" + BinarValue;
            }
            else if (BinarValue.length() == 1){
                BinarValue = "0" + "0" + "0" + "0" + BinarValue;
            }
            ChromosomeListBinar.add(i, BinarValue);
            ChromosomeListBinar2.add(i, BinarValue);
        }
        for ( int i=0; i<ChromosomesNumber; i++){
            PercentageValueList.add(i, 0.0);
        }

        for(int i = 0; i<ChromosomesNumber; i++){
            PercentageRangeList.add(i, new PercentageRangeClass(0.0, 0.0));

        }
        int Final = 0;
        for(;;) {
            double PercentageIterationValue = 0.00;
            double aValuePercent;
            int ValueOfFitFun = 0;

            for (int i = 0; i < ChromosomesNumber; i++) {
                int CurrValue = ValueList.get(i);
                ValueOfFitFun += CurrValue;
            }


            if (ValueOfFitFun > Final) {
                    Final = ValueOfFitFun;
                NumOfFitListIterations = 1;
            }
            else if (ValueOfFitFun == Final) {
                NumOfFitListIterations++;
            }
            if(NumOfFitListIterations == NumOfMaxFitFunOccur){
                    break;
            }

            for ( int i=0; i<ChromosomesNumber; i++){
                PercentageValueList.set(i, PercentageValue(ValueList.get(i), ValueOfFitFun));
            }

            for(int i = 0; i<ChromosomesNumber; i++){
                aValuePercent = PercentageIterationValue;
                PercentageIterationValue += PercentageValueList.get(i);
                PercentageRangeList.set(i, new PercentageRangeClass(aValuePercent, PercentageIterationValue));


            }

            for(int i = 0; i< ChromosomesNumber; i++){
                double x = RandomPercentageNumber();

                for(int j = 0; j < ChromosomesNumber; j++) {

                    if (x > PercentageRangeList.get(j).a && x < PercentageRangeList.get(j).b) {
                        ChromosomeList.set(i, ChromosomeList2.get(j));

                    }
                }
            }

        for(int i = 0; i < ChromosomesNumber; i++){
            String BinarValue = Integer.toBinaryString(ChromosomeList.get(i));
            if (BinarValue.length() == 4){
                BinarValue = "0" + BinarValue;
            }
            else if(BinarValue.length() == 3){
                BinarValue = "0" + "0" + BinarValue;
            }
            else if (BinarValue.length() == 2){
                BinarValue = "0" + "0" + "0" + BinarValue;
            }
            else if (BinarValue.length() == 1){
                BinarValue = "0" + "0" + "0" + "0" + BinarValue;
            }
            ChromosomeListBinar.set(i, BinarValue);
            ChromosomeListBinar2.set(i, BinarValue);
        }

        int PkhelperNumber = 0;

        for(int i = 0; i < ChromosomesNumber/2; i++){
            double x = RandomPercentageNumber();
            int random16 = Random16();
            int random162 = Random16();
            PkhelperNumber +=2;
            int Lokus = RandomNumberLokusPk();

            if(x < Pk){
                   String BegOneOf2 = ChromosomeListBinar2.get(random16-1).substring(0, Lokus);
                   String BegTwoOf2 = ChromosomeListBinar2.get(random162-1).substring(0, Lokus);
                   String EndOneOf2 = ChromosomeListBinar2.get(random16-1).substring(Lokus, ChromosomeListBinar.get(i).length());
                   String EndTwoOf2 = ChromosomeListBinar2.get(random162-1).substring(Lokus, ChromosomeListBinar.get(i).length());
                   String One = BegOneOf2+EndTwoOf2;
                   String Two = BegTwoOf2+EndOneOf2;
                   ChromosomeListBinar.set(PkhelperNumber-2, One);
                   ChromosomeListBinar.set(PkhelperNumber-1, Two);

               }

        }

        for(int i = 0; i < ChromosomesNumber; i++){
            double x = RandomPercentageNumber();
            int Lokus = RandomNumberLokusPm();
            if(x<Pm){
                String Beg = ChromosomeListBinar.get(i).substring(0,Lokus-1);
                String End = ChromosomeListBinar.get(i).substring(Lokus);
                String Mutate = ChromosomeListBinar.get(i).substring(Lokus-1, Lokus);

                if(Mutate.equals("0")){
                    String ChromosomeBinarFinal = Beg+"1"+End;
                    ChromosomeListBinar.set(i,ChromosomeBinarFinal);
                }
                else if(Mutate.equals("1")){
                    String ChromosomeBinarFinal = Beg+"0"+End;
                    ChromosomeListBinar.set(i,ChromosomeBinarFinal);
                }
            }

        }
        for(int i = 0; i < ChromosomesNumber; i++){
            int decimal=Integer.parseInt(ChromosomeListBinar.get(i),2);
            ChromosomeList.set(i, decimal);
        }
            for(int i = 0; i < ChromosomesNumber; i++){
                int j = CalcFun(a,b,c,d, ChromosomeList.get(i));
                ValueList.set(i,j);
                ChromosomeList2.set(i, ChromosomeList.get(i));
            }

        NumOfIterations++;

            }
        System.out.println(" | Liczba iteracji: " + NumOfIterations+ " | ");
        System.out.println(" | Suma funkcji przystosowania: "+ Final + " | ");
        for(int i = 0; i < ChromosomesNumber; i++){
            int j = i+1;
            System.out.print(" | CH"+j+ " = " + ChromosomeListBinar.get(i)+ " | ");
            System.out.print("Fenotyp = " + ChromosomeList.get(i)+ " | ");
            System.out.println("Funkcja przystosowania = " + ValueList.get(i)+ " | ");
        }

    }

    public static int RandomNumberChromosome() {
        Random r = new Random();
        return r.nextInt((31 - 1) + 1) + 1;
    }
    public static double RandomPercentageNumber() {
        return (Math.random() * (1));
    }
    public static int RandomNumberLokusPm() {
        Random r = new Random();
        return r.nextInt((5 - 1) + 1) + 1;
    }
    public static int RandomNumberLokusPk() {
        Random r = new Random();
        return r.nextInt((4 - 1) + 1) + 1;
    }
    public static int Random16() {
        Random r = new Random();
        return r.nextInt((6 - 1) + 1) + 1;
    }


    public static int CalcFun(int a, int b, int c, int d, int x){
        return (int) (a*(Math.pow(x, 3)) + b*(Math.pow(x, 2)) + c*x + d);
    }

    public static double PercentageValue(int a, int b){
        return (double) a / (double) b;
    }
}
