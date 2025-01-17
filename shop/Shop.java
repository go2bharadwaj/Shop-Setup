import java.util.Scanner;
public class Shop {
	static Scanner src = new Scanner(System.in);

	public static int intro(int choosen_function) {

		{
			System.out.println("This program supports 4 functions:");
			System.out.println("\t1. Setup Shop");
			System.out.println("\t2. Buy");
			System.out.println("\t3. List Items");
			System.out.println("\t4. Checkout");
			System.out.print("Please choose the function you want: ");
			choosen_function = src.nextInt();


		}		
		return choosen_function;
	}
	public static double AmtQual(double amtQual) {		

		// capturing amount
		System.out.println("");
		System.out.print("Enter the dollar amount to qualify for Additional Discount (or 0 if none offered): ");
		amtQual = src.nextInt();
		return amtQual;
	}

	public static double discQual(double DiscRate,double amtQual) {		
		// capturing Additional Discount rate
		if(amtQual != 0) {
			System.out.print("Enter the Additional Discount rate (e.g., 0.1 for 10%):");
			DiscRate = src.nextDouble();
			do {
				if(DiscRate <= 0 || DiscRate > 0.5){
					System.out.print("Invalid input. Enter a value > 0 and <= 0.5:");
					DiscRate = src.nextDouble();       
				}
			}while(DiscRate <= 0 || DiscRate > 0.5);
		}
		return DiscRate;
	}
	public static String[][] Setup(int numberOfitems) {
		double price = 0.0,discQual = 0.0;

		String str,str1;


		String[][] products = new String [4][numberOfitems];

		for(int i = 0; i <= numberOfitems - 1; i++) {
			switch(i) {
			case 0 : {
				System.out.print("Enter the name of the " + (i + 1) + "st product: ");
				break;
			}
			case 1: {    
				System.out.print("Enter the name of the " + (i + 1) + "nd product: ");
				break;
			}
			case 2: {   
				System.out.print("Enter the name of the " + (i + 1) + "rd product: ");
				break;
			}
			default: {
				System.out.print("Enter the name of the " + (i + 1) + "th product: ");
				break;
			}
			}
			// capturing price 
			products[0][i] = src.next();
			System.out.print("Enter the number of package price of " + (products[0][i]) + " :");
			price = src.nextDouble();
			str = Double.toString(price);
			products[1][i] = str;

			// capturing discount
			System.out.print("Enter the number of packages ('x') to qualify for Special Discount (buy 'x' get 1 free)\nfor "+ (products[0][i])  +" , or 0 if no Special Discount offered:");
			discQual = src.nextInt();
			str1 = Double.toString(discQual);
			products[2][i] = str1;
		}

		return products;

	}

	public static int[] buy(String[][] products,int numberOfitems) {

		int[] num_pack = new int[numberOfitems];
		for(int i = 0; i<=numberOfitems - 1; i++) {
			do {
				if(num_pack[i] < 0) {
					System.out.print("Invalid input. Enter a value >= 0: ");
					num_pack[i] = src.nextInt();
				}
				else {
					System.out.print("Enter the number of " + products[0][i] + " packages to buy: ");
					num_pack[i] = src.nextInt();
				}
			}while(num_pack[i] < 0);
		}
		return num_pack;
	}

	public static double[] priceCalc(String[][] products, int[] num_pack) {

		double[] Price_doub = new double[num_pack.length];
		double Pprice = 0.0; 
		for(int i = 0; i <= num_pack.length - 1; i++ ) {
			Pprice = Double.parseDouble(products[1][i]);
			Price_doub[i] = num_pack[i]*Pprice; 
		}
		return Price_doub;
	}


	public static double listItems(String[][] products, int[] num_pack){

		double[] Price_doub = new double[num_pack.length + 1];
		Price_doub = priceCalc(products,num_pack);
		int zero_product = 0;
		for(int i = 0; i <= num_pack.length - 1; i++) {
			if(num_pack[i] != 0) {
				String str1 =String.format("$%.2f", Double.parseDouble(products[1][i]));
				String str2 =String.format("$%.2f", Price_doub[i]);
				if(num_pack[i] == 1) {
					System.out.println(num_pack[i] + " package of " + products[0][i] + " @ " + str1 + " per pkg = " + str2);
				}
				else {
					System.out.println(num_pack[i] + " packages of " + products[0][i] + " @ " + str1 + " per pkg = " + str2); 
				}
			}
			else if(num_pack[i] == 0){
				zero_product++ ;
			}
		}

		return zero_product;
	}

	public static double OriginalSubtotal(double[] Price_doub) {
		double total = 0;
		for(int i = 0;i < Price_doub.length; i++) {
			total = Price_doub[i] + total; 
		}
		return total;
	}
	public static double SpecialDisc(int[] num_pack, double[] Price_doub,String[][] products) {
		int free_packs = 0;
		double SpecialDisctotal = 0.0;
		for(int i = 0; i < num_pack.length; i++) {
			double num1 = Double.parseDouble(products[2][i]) + 1;
			double num2 = Double.parseDouble(products[1][i]);
			free_packs = (int)(num_pack[i])/(int)(num1);
			SpecialDisctotal =  num2*free_packs + SpecialDisctotal;
		}
		return SpecialDisctotal;
	}
	public static double AdditionalDisc(double NewSubTotal,double DiscRate,double amtQual){
		double TotalAdditionalDisc = 0.0;
		if(NewSubTotal > amtQual) {
			TotalAdditionalDisc = NewSubTotal*DiscRate;
		}
		else {
			TotalAdditionalDisc = 0;
		}
		return TotalAdditionalDisc;
	}
	public static void summary(double[] Price_doub,int[] num_pack,double DiscRate,double amtQual,String[][] products) {
		double OrigSubtotal = OriginalSubtotal(Price_doub);
		double SpecDisc = SpecialDisc(num_pack, Price_doub,products);
		double NewSubTotal = OrigSubtotal - SpecDisc;
		double TotalAdditionalDisc = AdditionalDisc(NewSubTotal,DiscRate,amtQual);
		double FinalSubTotal = NewSubTotal - TotalAdditionalDisc;
		String str1 =String.format(" $%.2f", OrigSubtotal);
		String str2 =String.format("-$%.2f", SpecDisc);
		String str3 =String.format(" $%.2f", NewSubTotal);
		String str4 =String.format("-$%.2f", TotalAdditionalDisc);
		String str5 =String.format(" $%.2f", FinalSubTotal);
		System.out.println("Original Sub Total:           " + str1);
		if(SpecDisc != 0) 
		{
			System.out.println("Special Discounts:            " + str2);}
		else {
			System.out.println("No Special Discounts applied ");}
		System.out.println("New Sub Total:                " + str3);
		if(TotalAdditionalDisc != 0) {
			System.out.println("Additional %"+ (int)(DiscRate*100) +" Discount:      " + str4);}
		else {
			System.out.println("You did not qualify for an Additional Discount");
		}
		System.out.println("Final Sub Total:              " + str5);

	}




	public static void main(String[] args) {

		int ReRun = 0;
		do{ boolean[] flag = new boolean[4];
		flag[0] = false;
		flag[1] = false;
		flag[2] = false; 
		flag[3] = false;
		boolean allflag = false,check = false;
		int numberOfitems = 0;
		int choosen_function = 0;
		double zero_count;
		double DiscRate = 0.0;
		double amtQual = 0.0;
		int[] num_pack = new int[numberOfitems];
		double[] Price_doub = new double[num_pack.length];
		String[][] products = new String[2][choosen_function];
		do {if(ReRun == 1) {
			ReRun = 0;
			flag[0] = false;
			flag[1] = false;
			flag[2] = false; 
			flag[3] = false;
			numberOfitems = 0;
			choosen_function = 0;
			DiscRate = 0.0;
			amtQual = 0.0;
		}

		//---------------------------------------------------------------------------------------------------------------------------------------------------------
		if(check == false ) {
			choosen_function = intro(choosen_function);
		}
		switch(choosen_function){
		case 0: 
		{   check = false;
		System.out.print("\nInvalid input, please enter valid function number(ie..1 to 4):\n");
		System.out.println("");
		break;
		}
		//---------------------------------------------------------------------------------------------------------------------------------------------------------
		case 1: {
			check = false;
			if(flag[0] == false) { 
				flag[0] = true;
			}
			System.out.print("Please enter the number of items to setup shop:  ");
			numberOfitems = src.nextInt();
			System.out.println("");
			products = Setup(numberOfitems);
			amtQual = AmtQual(amtQual);
			DiscRate = discQual(DiscRate,amtQual);
			System.out.println("");
			break;

		}
		//------------------------------------------------------------------------------------------------------------------------------------------------------------
		case 2:{
			check = false;
			if(flag[0] == true && flag[1] == false || flag[0] == false && flag[1] == true) 
			{
				flag[1] = true;
			}
			else if(flag[0] == false) {
				System.out.print("\nShop is not set up yet\n");
				System.out.println("");
				break;
			}
			System.out.println("");
			num_pack = buy(products,numberOfitems);
			System.out.println("");
			break;
		}
		//----------------------------------------------------------------------------------------------------------------------------------------------------------
		case 3: {
			check = false;
			if(flag[0] == true && flag[1] == true && flag[2] == false) {
				flag[2] = true;
			}

			else if(flag[0] == true && flag[1] == false) {
				System.out.print("\nYou have not bought anything! \n");
				System.out.println("");
				break;
			}

			else if(flag[0] == false) {
				System.out.print("\nShop is not set up yet!\n");
				System.out.println("");
				break;
			}
			System.out.println("");
			zero_count = listItems(products,num_pack);
			Price_doub = priceCalc(products,num_pack);
			if(zero_count == num_pack.length) {
				System.out.println("No items were purchased.");
			}
			System.out.println("");
			break;
		}
		//----------------------------------------------------------------------------------------------------------------------------------------------------------
		case 4: { 
			check = false;
			if(flag[0] == true && flag[1] == true && flag[2] == true && flag[3] == false) {
				flag[3] = true;
			}
			else if(flag[0] == true && flag[1] == false) {
				System.out.print("\nYou have not bought anything!\n");
				System.out.println("");
				break;}
			else if(flag[0] == false) {
				System.out.print("\nShop is not set up yet!\n");
				System.out.println("");
				break;}

			{
				System.out.println("");
				summary(Price_doub, num_pack, DiscRate, amtQual, products);
				System.out.println("");
				System.out.println("Thanks for coming!");
				System.out.println("");
				System.out.println("-------------------------------------------------");
				System.out.print("Would you like to re-run (1 for yes, 0 for no)? ");
				ReRun = src.nextInt();
				System.out.println("-------------------------------------------------");

				break;
			}
		}
		//----------------------------------------------------------------------------------------------------------------------------------------------------------
		default:{
			do {
				if(choosen_function < 0 || choosen_function >= 5 ) {
					System.out.print("Invalid Input. Enter a value > 0 and < 5: ");
					choosen_function = src.nextInt();
					check = true;
				}}while(choosen_function < 0 || choosen_function >= 5);

			break;}

		}
		if(flag[0] == true && flag[1] == true && flag[2] == true && flag[3] == true) {
			allflag = true;
		}
		}while((choosen_function < 0 || choosen_function < 5) && allflag != true);

		} while(ReRun == 1);

		src.close();
	}

}//Shop.java program written by Bharadwaj Satyanarayana. Project for CSE 021 class spring 2019


