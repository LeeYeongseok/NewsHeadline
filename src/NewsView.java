
public class NewsView implements Observer {
	private String title;
	private String content;
	private String url;
	private String photourl;
	private Boolean isScrap;
	private NewsData newsData;
	
	public NewsView(NewsData newsData, int viewMode) { // viewMode = 1 �̸� �ǽð� ����, viewMode = 2 �̸� ��ũ���� ���� 
		this.title = "";
		this.content = "";
		this.url = "";
		this.photourl = "";
		this.isScrap = false;
		this.newsData = newsData;
		if(viewMode == 1) {
			newsData.registerObserver(this);
		}else if(viewMode == 2) {
			newsData.registerScrapObserver(this);
		}
	}
	
	// �ڱ��ڽ� ���������� �����ϱ�
	public void removeMe() {
		newsData.removeObserver(this);
		newsData.removeScrapObserver(this);
	}
	
	// ������� ������ ������Ʈ �ؼ� ���
	@Override
	public void updateArticles(NewsArticles newsArticles, int index) {
		// View�� ���� ������ �ִ� �������� �ٸ� ������ �޴´ٸ�
		if(this.url != newsArticles.url || this.isScrap != newsData.isScrap(url)) {
			System.out.println(index);
			this.title = newsArticles.title;
			this.content = newsArticles.content;
			this.url = newsArticles.url;
			this.photourl = newsArticles.photourl;
			this.isScrap = newsData.isScrap(url);
			
			System.out.println("title : " + this.title);
			System.out.println("content : " + this.content);
			System.out.println("url : " + this.url);
			System.out.println("photourl : " + this.photourl);
			if(isScrap)
				System.out.println("��ũ�� O");
			else
				System.out.println("��ũ�� X");
			System.out.println("");
		}
	}


	// ��ũ�� ��� ������Ʈ �ؼ� ���
	@Override
	public void updateScrapArticles(NewsArticles newsArticles, int index) {
		System.out.println(index);

		this.title = newsArticles.title;
		this.content = newsArticles.content;
		this.url = newsArticles.url;
		this.photourl = newsArticles.photourl;
		this.isScrap = newsData.isScrap(url);
		
		System.out.println("title : " + this.title);
		System.out.println("content : " + this.content);
		System.out.println("url : " + this.url);
		System.out.println("photourl : " + this.photourl);
		System.out.println("");
	}

	
}
