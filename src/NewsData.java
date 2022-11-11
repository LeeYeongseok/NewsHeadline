import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class NewsData implements Subject {
	private List<Observer> observers;
	private List<Observer> scrapObserver;
	private List<NewsArticles> articles;
	private List<NewsArticles> scrapArticles;
	private ArticlesManager articlesManager;
	
	// NewsData 생성자
	public NewsData(){
		observers = new ArrayList<>();
		scrapObserver = new ArrayList<>();
		articles = new ArrayList<>();
		scrapArticles = new ArrayList<>();
		articlesManager = new ArticlesManager();
	}
	
	// observer 추가
	@Override
	public void registerObserver(Observer O) {
		observers.add(O);
	}
	
	// observer 제거
	@Override
	public void removeObserver(Observer O) {
		observers.remove(O);
	}
	
	// scrapObserver 추가
	@Override
	public void registerScrapObserver(Observer O) {
		scrapObserver.add(O);
	}

	// scrapObserver 삭제
	@Override
	public void removeScrapObserver(Observer O) {
		scrapObserver.remove(O);
	}
	
	@Override
	public void notifyObserver() {
		for (int i = 0; i < observers.size(); i++) {
			observers.get(i).updateArticles(articles.get(i), i+1);
		}
		for (int i = 0; i < Math.min(scrapObserver.size(),scrapArticles.size()); i++) {
			scrapObserver.get(i).updateScrapArticles(scrapArticles.get(i), i+1);
			//scrapObserver.get(i).showArticles(i+1);
		}
	}
	
	
	// 서버에서 뉴스기사 불러오기
	public void getArticles() {
		// 서버에서 뉴스기사 가져와서 articles에 저장하기
		articles =  articlesManager.getArticles();
		notifyObserver();
	}
	
	// 파일에서 스크랩된 뉴스기사 불러오기
	public void getScrapArticles() {
		// 파일에서 뉴스기사 가져와서 scrapArticles에 저장하기
		scrapArticles.clear();
		scrapArticles = articlesManager.getScrapArticles();

	}
	
	
	// 해당 숫자의 뉴스 스크랩하기
	public void scrapNews(int scrapNum) {
		articlesManager.saveScrapNews(articles.get(scrapNum));
		getScrapArticles();
		notifyObserver();
	}

	
	// 가지고 있는 스크랩 기사의 개수
	public int getNumberOfScrapArticles() {
		return scrapArticles.size();
	}

	// 스크랩 되어 있는지 확인
	public Boolean isScrap(String url) {
		for(int i = 0; i < scrapArticles.size(); i++) {
			if(scrapArticles.get(i).url.equals(url)){
				return true;
			}
		}
		return false;
	}

	
	
}
