import org.json.simple.parser.ParseException;
import java.util.Scanner;

public class Program {
	
	public static void main(String[] args) throws ParseException{

		Controller controller = new Controller();
		
		int viewNum = 5;
		System.out.println("�ǽð� ���");
		controller.newsHome(viewNum);
		
		System.out.println("��ũ���� ���");
		controller.newsScrap();
		
		int scrapIndex;
		
		while(true) {
			System.out.println("��ũ���ϰ���� ����� ���ڸ� �Է��ϼ���. �����Ϸ��� 0 �Է�");
			Scanner sc = new Scanner(System.in);
			scrapIndex = sc.nextInt();
			if(scrapIndex != 0) {
				// ���� View���� Ŭ��������, �̺�Ʈ�� �����ؾ� ������  
				// console�� �Է¿� ���� ����ǵ��� �ϹǷ� Controller�� view���� listen �ϴ� �κ��� ������
				controller.scrapNews(scrapIndex-1);
			}else {
				break;
			}
		}
		
	}
	
	
}
