import java.util.ArrayList;
import java.util.List;

public class Controller implements IController {

	private NewsData newsData;
	private List<NewsView> newsViews;
	private List<NewsView> scrapNewsViews;
	
	public Controller() {
		this.newsData = new NewsData();
		scrapNewsViews = new ArrayList<NewsView>();
		newsData.getScrapArticles();
	}
	
	// �ǽð� ���� �ѱ�
	public void newsHome(int numOfView) {
		// �並 ����� �������� �������� �߰��� �Ŀ�
		newsViews = new ArrayList<NewsView>();
		for(int i = 0; i< numOfView; i++) {
			newsViews.add(new NewsView(newsData, 1));
		}
		
		// ��縦 �������� �䰡 ����
		newsData.getArticles();	
	}
	
	// ��ũ�� ���� �ѱ�
	public void newsScrap() {
		newsData.getScrapArticles();
		for(int i = 0; i < scrapNewsViews.size(); i++) {
			scrapNewsViews.get(i).removeMe();
		}
		scrapNewsViews = new ArrayList<NewsView>();
		for(int i = 0; i< 10; i++) {
			scrapNewsViews.add(new NewsView(newsData, 2));
		}
		newsData.notifyObserver();
	}
	
	
	@Override
	public void touchNews() {
		// TODO Auto-generated method stub
		
	}

	// ���� ��ũ���ϱ�
	@Override
	public void scrapNews(int scrapNum) {
		// TODO Auto-generated method stub
		newsData.scrapNews(scrapNum);
	}

}
