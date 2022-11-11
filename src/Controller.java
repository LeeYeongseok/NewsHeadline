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
	
	// 실시간 뉴스 켜기
	public void newsHome(int numOfView) {
		// 뷰를 만들고 데이터의 옵저버에 추가한 후에
		newsViews = new ArrayList<NewsView>();
		for(int i = 0; i< numOfView; i++) {
			newsViews.add(new NewsView(newsData, 1));
		}
		
		// 기사를 가져오면 뷰가 변경
		newsData.getArticles();	
	}
	
	// 스크랩 뉴스 켜기
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

	// 뉴스 스크랩하기
	@Override
	public void scrapNews(int scrapNum) {
		// TODO Auto-generated method stub
		newsData.scrapNews(scrapNum);
	}

}
