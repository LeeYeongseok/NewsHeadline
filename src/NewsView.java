
public class NewsView implements Observer {
	private String title;
	private String content;
	private String url;
	private String photourl;
	private Boolean isScrap;
	private NewsData newsData;
	
	public NewsView(NewsData newsData, int viewMode) { // viewMode = 1 이면 실시간 뉴스, viewMode = 2 이면 스크랩한 뉴스 
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
	
	// 자기자신 옵저버에서 제외하기
	public void removeMe() {
		newsData.removeObserver(this);
		newsData.removeScrapObserver(this);
	}
	
	// 뉴스기사 데이터 업데이트 해서 출력
	@Override
	public void updateArticles(NewsArticles newsArticles, int index) {
		// View가 원래 가지고 있던 뉴스기사와 다른 정보를 받는다면
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
				System.out.println("스크랩 O");
			else
				System.out.println("스크랩 X");
			System.out.println("");
		}
	}


	// 스크랩 기사 업데이트 해서 출력
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
