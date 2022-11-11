import org.json.simple.parser.ParseException;
import java.util.Scanner;

public class Program {
	
	public static void main(String[] args) throws ParseException{

		Controller controller = new Controller();
		
		int viewNum = 5;
		System.out.println("실시간 기사");
		controller.newsHome(viewNum);
		
		System.out.println("스크랩한 기사");
		controller.newsScrap();
		
		int scrapIndex;
		
		while(true) {
			System.out.println("스크랩하고싶은 기사의 숫자를 입력하세요. 종료하려면 0 입력");
			Scanner sc = new Scanner(System.in);
			scrapIndex = sc.nextInt();
			if(scrapIndex != 0) {
				// 원래 View에서 클릭했을때, 이벤트로 실행해야 하지만  
				// console의 입력에 의해 실행되도록 하므로 Controller가 view에서 listen 하는 부분은 생략함
				controller.scrapNews(scrapIndex-1);
			}else {
				break;
			}
		}
		
	}
	
	
}
