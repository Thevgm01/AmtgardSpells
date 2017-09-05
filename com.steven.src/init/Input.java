package init;
import java.util.Random;
import java.util.Scanner;

public class Input {

	private Scanner in;
	private Random randy;
	
	public Input() {
		in = new Scanner(System.in);
		randy = new Random();
	}
	
	public String next() {
		return in.nextLine();
	}
	
	public char yn() {
		boolean retry;
		do{
			retry = false;
			String cur = in.nextLine();
			if(cur.toLowerCase().equals("y")) return 'y';
			else if(cur.toLowerCase().equals("n")) return 'n';
			else{
				System.out.println(getErrorMessage());
				retry = true;
			}
		} while(retry);
		return '\0';
	}
	
	public int intRange(int low, int high) { return intRange(low, high, ""); }
	public int intRange(int low, int high, String escape) {
		boolean retry;
		do{
			retry = false;
			String cur = in.nextLine();
			if(escape.length() > 0 && cur.toLowerCase().equals(escape)) break;
			try{
				int i = Integer.parseInt(cur);
				if(i < low || i > high) throw new IndexOutOfBoundsException();
				return i;
			} catch(Exception e) {
				System.out.println(getErrorMessage());
				retry = true;
			}
		} while(retry);
		return low-1;
	}
	
	private String getErrorMessage() {
		switch(randy.nextInt(24)){
	        case 0:  return "I don't understand.";
	        case 1:  return "That doesn't make sense.";
	        case 2:  return "Try to be a little more clear.";
	        case 3:  return "Ask again later.";
	        case 4:  return "Could you repeat that?";
	        case 5:  return "Come again?";
	        case 6:  return "Please be more sensible.";
	        case 7:  return "I don't get it.";
	        case 8:  return "Can you be more clear?";
	        case 9:  return "Try making more sense.";
	        case 10: return "Get a hold of yourself.";
	        case 11: return "Get a grip.";
	        case 12: return "Uh... no.";
	        case 13: return "Calm yourself.";
	        case 14: return "Just calm down.";
	        case 15: return "Excuse me?";
	        case 16: return "Are you brain-dead?";
	        case 17: return "You're a hooligan.";
	        case 18: return "You're a moron.";
	        case 19: return "You're an idiot.";
	        case 20: return "You're a dummy.";
	        case 21: return "Stop.";
	        case 22: return "Incorrect.";
	        case 23: return "Nope.";
	        default: return "Don't read this.";
		}
	}
	
}
