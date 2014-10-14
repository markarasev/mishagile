package view;

import claim.*;
import client.Client;
import client.ClientManager;
import communication.Form;
import communication.FormType;
import user.UserAccount;

import java.util.Scanner;

/**
 * Created by root on 11/10/14.
 */
public class MainMenuView extends View {

	// Attributes
	private UserAccount ua;
	private ClientManager cm;
	private ClaimManager claimManager;

	// Constructors
	public MainMenuView(UserAccount um, ClientManager cm,
			ClaimManager claimManager) {
		super();
		this.ua = um;
		this.cm = cm;
		this.claimManager = claimManager;
		display();
	}

	public MainMenuView() {
		this(new UserAccount(), new ClientManager(), new ClaimManager());
	}

	// Methods
	protected void display() {
		clear();

		switch (ua.getRank()) {

		case ACD:
			menuAcd();
			break;

		case BCD:
			menuBcd();
			break;

		case CD:
			menuCd();
			break;

		case FIN:
			menuFin();
			break;

		}

		mainMenuReturn();
	}

	private void printSharedMenu() {
		System.out
				.println("1. Check if client insured\n2. Search claim\n3. Send form to client\n4. Create client\n5. Register claim\n0. Logout");
	}

	// TODO refactor?
	private void menuAcd() {
		printSharedMenu();
		System.out.print("\nPlease choose an option:");
		String option = sc.nextLine();

		// TODO: Remplacer 4 par le nombre de choix
		while (!inputCheck(option, 6)) {
			System.out.print("\nPlease choose a VALID option: ");
			option = sc.nextLine();
		}

		switch (Integer.parseInt(option)) {
		case 1:
			checkClient();
			break;
		case 2:
			new SearchClaimView(ua, claimManager);
			break;
		case 3:
			sendForm();
			break;
		case 4:
			addClient();
			break;
		case 5:
			addClaim();
			break;
        case 6:
            checkForm();
            break;
		case 0:
			new LoginView();
		}
	}

	private void menuBcd() {
		printSharedMenu();
		System.out.print("\nPlease choose an option:");
		String option = sc.nextLine();

		// TODO: Remplacer 4 par le nombre de choix
		while (!inputCheck(option, 6)) {
			System.out.print("\nPlease choose a VALID option: ");
			option = sc.nextLine();
		}

		switch (Integer.parseInt(option)) {
		case 1:
			checkClient();
			break;
		case 2:
			new SearchClaimView(ua, claimManager);
			break;
		case 3:
			sendForm();
			break;
		case 4:
			addClient();
			break;
		case 5:
			addClaim();
			break;
        case 6:
            checkForm();
            break;
		case 0:
			new LoginView();
		}
	}

	private void menuCd() {
		printSharedMenu();
		System.out.print("\nPlease choose an option:");
		String option = sc.nextLine();

		// TODO: Remplacer 4 par le nombre de choix
		while (!inputCheck(option, 5)) {
			System.out.print("\nPlease choose a VALID option: ");
			option = sc.nextLine();
		}

		switch (Integer.parseInt(option)) {
		case 1:
			checkClient();
			break;
		case 2:
			new SearchClaimView(ua, claimManager);
			break;
		case 3:
			sendForm();
			break;
		case 4:
			addClient();
			break;
		case 5:
			addClaim();
			break;
		case 0:
			new LoginView();
		}
	}

	private void menuFin() {
		printSharedMenu();
		System.out.print("\nPlease choose an option:");
		String option = sc.nextLine();

		// TODO: Remplacer 4 par le nombre de choix
		while (!inputCheck(option, 5)) {
			System.out.print("\nPlease choose a VALID option: ");
			option = sc.nextLine();
		}

		switch (Integer.parseInt(option)) {
		case 1:
			checkClient();
			break;
		case 2:
			new SearchClaimView(ua, claimManager);
			break;
		case 3:
			sendForm();
			break;
		case 4:
			addClient();
			break;
		case 5:
			addClaim();
			break;
		case 0:
			new LoginView();
		}
	}

	private void mainMenuReturn() {
		System.out.println("\n\nPress enter to get back to the main menu... ");
		sc.nextLine();
		new MainMenuView(this.ua, this.cm, this.claimManager);
	}

	// asks user to enter a name and a firstname and checks if he's insured
	private void checkClient() {
		String[] names = askClientNames();

		if (cm.checkClient(names[0], names[1]) != null) {
			System.out.println("Client insured.");
		} else {
			System.out.println("Client not insured.");
		}
	}

	// sends a form to a given client
	private void sendForm() {
		String[] names = askClientNames();
        FormType type = askFormType();

        if (cm.sendForm(names[0], names[1], type)) {
            System.out.println("Sending successfull");
        } else {
            System.out.println("Error client does not exist.");
        }
	}

	private Client addClient() {
		String[] names = askClientNames();

		if (cm.checkClient(names[0], names[1]) == null) {
			do {
				System.out.print("\nInsurance (A, B, C or D): ");
			} while (!cm.addClient(names[0], names[1], sc.nextLine().charAt(0)));
		}

		else {
			System.out.println("Client already exists.");
		}

		return cm.checkClient(names[0], names[1]);
	}

	private boolean isInt(String input) {
		try {
			int test = Integer.parseInt(input);
		} catch (NumberFormatException e) {
			return false;
		}
		return true;
	}

	private void addClaim() {
		Client client = addClient();
		String date, carHistory, carPrice, damageCost;
		System.out.print("Date: ");
		date = sc.nextLine();
		System.out.print("\nCar history: ");
		carHistory = sc.nextLine();
		System.out.print("\nCar price: ");
		carPrice = sc.nextLine();

		while (!isInt(carPrice)) {
			System.out.print("\nPlease enter a VALID price: ");
			carPrice = sc.nextLine();
		}

		System.out.print("\nDamage cost: ");
		damageCost = sc.nextLine();

		while (!isInt(damageCost)) {
			System.out.print("\nPlease enter a VALID cost: ");
			damageCost = sc.nextLine();
		}
		Claim newClaim = new Claim(Integer.parseInt(carPrice),
				Integer.parseInt(damageCost), carHistory,
				ClaimStatus.REGISTERED, client, date, Category.undefined, Decision.undefined);
		claimManager.addClaim(newClaim);
		cm.addClaimToClient(client, newClaim);
	}

    private void checkForm() {
        String[] names = askClientNames();
        Form form = cm.checkForm(names[0], names[1], askFormType());

        System.out.println(form);
    }

    private FormType askFormType() {
        System.out.println("What kind of form ? (A, B, or C)");
        do {
            switch (sc.nextLine().charAt(0)) {
                case 'A':
                    return FormType.A;
                case 'a':
                    return FormType.A;
                case 'B':
                    return FormType.B;
                case 'b':
                    return FormType.B;
                case 'C':
                    return FormType.C;
                case 'c':
                    return FormType.C;
                default:
                    System.out
                            .println("Please enter a correct input. (A, B or C)");
            }
        } while (true);
    }
}