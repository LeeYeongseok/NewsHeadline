import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class NewsData implements Subject {
	private List<Observer> observers;
	private List<Observer> scrapObserver;
	private List<NewsArticles> articles;
	private List<NewsArticles> scrapArticles;
	private ArticlesManager articlesManager;
	
	// NewsData ������
	public NewsData(){
		observers = new ArrayList<>();
		scrapObserver = new ArrayList<>();
		articles = new ArrayList<>();
		scrapArticles = new ArrayList<>();
		articlesManager = new ArticlesManager();
	}
	
	// observer �߰�
	@Override
	public void registerObserver(Observer O) {
		observers.add(O);
	}
	
	// observer ����
	@Override
	public void removeObserver(Observer O) {
		observers.remove(O);
	}
	
	// scrapObserver �߰�
	@Override
	public void registerScrapObserver(Observer O) {
		scrapObserver.add(O);
	}

	// scrapObserver ����
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
	
	
	// �������� ������� �ҷ�����
	public void getArticles() {
		// �������� ������� �����ͼ� articles�� �����ϱ�
		articles =  articlesManager.getArticles();
		notifyObserver();
	}
	
	// ���Ͽ��� ��ũ���� ������� �ҷ�����
	public void getScrapArticles() {
		// ���Ͽ��� ������� �����ͼ� scrapArticles�� �����ϱ�
		scrapArticles.clear();
		scrapArticles = articlesManager.getScrapArticles();

	}
	
	
	// �ش� ������ ���� ��ũ���ϱ�
	public void scrapNews(int scrapNum) {
		articlesManager.saveScrapNews(articles.get(scrapNum));
		getScrapArticles();
		notifyObserver();
	}

	
	// ������ �ִ� ��ũ�� ����� ����
	public int getNumberOfScrapArticles() {
		return scrapArticles.size();
	}

	// ��ũ�� �Ǿ� �ִ��� Ȯ��
	public Boolean isScrap(String url) {
		for(int i = 0; i < scrapArticles.size(); i++) {
			if(scrapArticles.get(i).url.equals(url)){
				return true;
			}
		}
		return false;
	}

	
	
}
