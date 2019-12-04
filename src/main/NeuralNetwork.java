package main;

import java.io.*;
import java.util.*;

public class NeuralNetwork {
	
	Application a = new Application();
	Scanner scan;
	File file;
	
	int id, value, amountOfExceptions = 0, time;
	
	int exceptions[][] = new int[20000][2];
	int input[] = new int[4];
	double L1[] = new double[3];
	double L2[] = new double[4];
	double L2W[][] = new double[3][3];
	double L2E[] = new double[3];
	double L3[] = new double[4];
	double L3W[][] = new double[3][4];
	double L3E[] = new double[4];
	double L4;
	double L4W[] = new double[4];
	double L4E;
	
	double sum, iterations = 0, learnC = 0.5, error = 0, eV, tmp;
	
	String inputLine;
	
	public boolean learned, exit = false, eof = false, isInExc = false;
	
	public void start() {
		file = new File("input.txt");
		try {
			scan = new Scanner(file);
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		eV = Math.E;
		L1[2] = 1;
		L2[3] = 1;
		L3[3] = 1;
		for(int i = 0;i < 3;i++) {
			for(int j = 0;j < 3;j++) {
				L2W[i][j] = Math.random();
			}
		}
		for(int i = 0;i < 3;i++) {
			for(int j = 0;j < 4;j++) {
				L3W[i][j] = Math.random();
			}
		}
		for(int i = 0;i < 4;i++) {
			L4W[i] = Math.random();
		}
		if(!learned) {
			for(;;) {
				cycle();
				if(exit) {
					break;
				}
			}
		}
		System.out.println("Completed learning. Iterations: " + iterations);
		int tmp[] = new int[2];
		tmp[0] = tmp[1] = 3;
		writeW(L2W, tmp);
		tmp[0] = 3;tmp[1] = 4;
		writeW(L3W, tmp);
		System.out.println("Global error: " + error);
		a.writeln("Enter number of command:");
		a.writeln("1 - enter exception");
		a.writeln("2 - calculate");
		while(true) {
			inputLine = a.get(inputLine);
			if(inputLine.charAt(0) == '1') {
				a.writeln("Enter id of plug:");
				while(true) {
					try {
						id = a.get(id);
						break;
					}catch(Exception e) {}
				}
				a.writeln("Enter value to unlimit: 1 - yes, 0 - no");
				while(true) {
					try {
						value = a.get(value);
						break;
					}catch(Exception e) {}
				}
				for(int i = 0;i < amountOfExceptions;i++) {
					if(id == exceptions[i][0]) {
						exceptions[i][1] = value;
						break;
					}
					exceptions[amountOfExceptions][0] = id;
					exceptions[amountOfExceptions][1] = value;
					amountOfExceptions++;
				}
			}
			if(inputLine.charAt(0) == '2') {
				a.writeln("Enter id of plug:");
				while(true) {
					try {
						id = a.get(id);
						break;
					}catch(Exception e) {}
				}
				a.writeln("Enter amount of Watt:");
				while(true) {
					try {
						value = a.get(value);
						break;
					}catch(Exception e) {}
				}
				a.writeln("Enter time in minutes:");
				while(true) {
					try {
						time = a.get(time);
						break;
					}catch(Exception e) {}
				}
				for(int i = 0;i < exceptions.length;i++) {
					if(id == exceptions[i][0]) {
						isInExc = true;
						if(exceptions[i][1] == 1) {
							a.writeln("Everything is ok.");
						}else {
							isInExc = false;
						}
					}
				}
				if(!isInExc) {
					formatToArray(value, time);
					a.writeln(calculate());
				}else {
					isInExc = false;
				}
			}
		}
	}
	
	void cycle() {
		error = 0;
		while(!eof) {
			updateInput();
			iterations++;
			L1[0] = input[1];
			L2[1] = input[2];
			for(int i = 0;i < 3;i++) {
				sum = 0;
				for(int j = 0;j < 3;j++) {
					sum += L2W[i][j]*L1[j];
				}
				L2[i] = func(sum);
			}
			for(int i = 0;i < 3;i++) {
				sum = 0;
				for(int j = 0;j < 4;j++) {
					sum += L3W[i][j]*L2[j];
				}
				L3[i] = func(sum);
			}
			sum = 0;
			for(int i = 0;i < 4;i++) {
				sum += L4W[i]*L3[i];
			}
			L4 = func(sum);
			
			//
			
			if((double)input[3] == 1) {
				tmp = 0.8;
			}else {
				tmp = 0.2;
			}
			L4E = (tmp - L4);
			error+=L4E;
			L4E*=Dfunc(L4);
			for(int i = 0;i < 3;i++) {
				L3E[i] = 0;
				L3E[i] += L4E * L4W[i];
				L3E[i]*=Dfunc(L3[i]);
			}
			for(int i = 0;i < 3;i++) {
				for(int j = 0;j < 3;j++) {
					L2E[i] = 0;
					L2E[i] += L3E[j] * L3W[j][i];
				}
				L2E[i]*=Dfunc(L2[i]);
			}
			
			//
			
			for(int i = 0;i < 3;i++) {
				for(int j = 0;j < 3;j++) {
					L2W[i][j] += learnC*L2E[i]*Dfunc(L2[i])*L1[j];
				}
			}
			for(int i = 0;i < 3;i++) {
				for(int j = 0;j < 4;j++) {
					L3W[i][j] += learnC*L3E[i]*Dfunc(L3[i])*L2[j];
				}
			}
			for(int i = 0;i < 4;i++) {
				L4W[i] += learnC*L4E*Dfunc(L4)*L3[i];
			}
		}
		eof = false;
		error = Math.abs(error);
		if(error < 0.2) {
			exit = true;
		}else {
			System.out.println(error);
		}
	}
	
	public double calculate() {
		L1[0] = input[1];
		L2[1] = input[2];
		for(int i = 0;i < 3;i++) {
			sum = 0;
			for(int j = 0;j < 3;j++) {
				sum += L2W[i][j]*L1[j];
			}
			L2[i] = func(sum);
		}
		for(int i = 0;i < 3;i++) {
			sum = 0;
			for(int j = 0;j < 4;j++) {
				sum += L3W[i][j]*L2[j];
			}
			L3[i] = func(sum);
		}
		sum = 0;
		for(int i = 0;i < 4;i++) {
			sum += L4W[i]*L3[i];
		}
		L4 = func(sum);
		return L4;
	}
	
	public double func(double a) {
		a = 1/(1+Math.pow(eV,-a));
		return a;
	}
	
	public double Dfunc(double a) {
		a = a*(1-a);
		return a;
	}
	
	void updateInput() {
		if(scan.hasNextInt()) {
			input[0] = scan.nextInt();
			input[1] = scan.nextInt();
			input[2] = scan.nextInt();
			input[3] = scan.nextInt();
		}else {
			eof = true;
			scan.close();
			try {
				scan = new Scanner(file);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
	}
	int writeW(double arr[][], int size[]) {
		try {
		FileWriter fileWriter = new FileWriter("NN.w");
		for(int i = 0; i < size[0]; i++)
			for(int j = 0; j < size[1]; j++)
				fileWriter.write(arr[i][j]+"");
		}catch (IOException e) {
			System.out.println("Friting file exception: NN.w file could't open: "+e.getMessage());
		}
		return 0;		
	}

	void formatToArray(int val1, int val2) {
		input[1] = val1;
		input[2] = val2;
	}
}
