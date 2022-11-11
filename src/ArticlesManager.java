import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.http.HttpClient;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;



public class ArticlesManager {
	private String apiUrl = "https://newsapi.org/v2/top-headlines?country=kr&apiKey=6f9149c6247840f9947fb200def35f74";
	
	
	// json으로 뉴스기사 받아오기
	public String apiGet()
	{
	    URL url = null;
	    String readLine = null;
	    StringBuilder buffer = null;
	    BufferedReader bufferedReader = null;
	    BufferedWriter bufferedWriter = null;
	    HttpURLConnection urlConnection = null;
	        
	    int connTimeout = 5000;
	    int readTimeout = 3000;
	        	        
	    try 
	    {
	        url = new URL(apiUrl);
	        urlConnection = (HttpURLConnection)url.openConnection();
	        urlConnection.setRequestMethod("GET");
	        urlConnection.setConnectTimeout(connTimeout);
	        urlConnection.setReadTimeout(readTimeout);
	        urlConnection.setRequestProperty("Accept", "application/json;");
	            
	        buffer = new StringBuilder();
	        if(urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK) 
	        {
	            bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(),"UTF-8"));
	            while((readLine = bufferedReader.readLine()) != null) 
	            {
	                buffer.append(readLine).append("\n");
	            }
	        }
	        else 
	        {
	            buffer.append("code : ");
	            buffer.append(urlConnection.getResponseCode()).append("\n");
	            buffer.append("message : ");
	            buffer.append(urlConnection.getResponseMessage()).append("\n");
	        }
	    }
	    catch(Exception ex) 
	    {
	    	System.out.println("에러");
	    }
	    finally 
	    {
	        try 
	        {
	            if (bufferedWriter != null) { bufferedWriter.close(); }
	            if (bufferedReader != null) { bufferedReader.close(); }
	        }
	        catch(Exception ex) 
	        { 
	        	System.out.println("에러");
	        }
	    }
                
        return buffer.toString();
    }

	 
	// 뉴스기사 받아와서 parsing해서 NewsArticles 10개짜리 객체 리스트 반환
	public List<NewsArticles> getArticles(){
		String jsonData = apiGet();			
		return parsingServerJson(jsonData);
    }
	
	// 서버 json 데이터 파싱해서 List<NewsArticles> 꼴로 만들기
	public List<NewsArticles> parsingServerJson(String jsonData){
		try {
			JSONObject objData = (JSONObject)new JSONParser().parse(jsonData);
			JSONArray arrData = (JSONArray)objData.get("articles");
			StringBuilder sb= new StringBuilder();
			List<NewsArticles> articles = new ArrayList<>();
			
			JSONObject tmp;
			JSONArray tmpArr;
			for(int i=0; i<Math.min(10, arrData.size()); i++){
				NewsArticles tmpArticles = new NewsArticles();
				String title;
				String content;
				String url;
				String photourl;
				
				tmp = (JSONObject)arrData.get(i);
				//제목 title
				title = (String) tmp.get("title");
				//내용 description
				content = (String) tmp.get("description");
				//url url
				url = (String) tmp.get("url");
				//사진 urlToImage
				photourl = (String) tmp.get("urlToImage");
				
				tmpArticles.title = title;
				tmpArticles.content = content;
				tmpArticles.url = url;
				tmpArticles.photourl = photourl;
				
				articles.add(tmpArticles);
			}
			return articles;
		} catch (Exception e){
			List<NewsArticles> articles = new ArrayList<>();
			return articles;
		}
	}
	
	
	// 파일 데이터 json 데이터 파싱해서 List<NewsArticles> 꼴로 만들기
		public List<NewsArticles> parsingFileJson(String jsonData){
			try {
				JSONArray arrData = (JSONArray)new JSONParser().parse(jsonData);
				StringBuilder sb= new StringBuilder();
				List<NewsArticles> articles = new ArrayList<>();
				
				JSONObject tmp;
				JSONArray tmpArr;
				for(int i=0; i<arrData.size(); i++){
					NewsArticles tmpArticles = new NewsArticles();
					String title;
					String content;
					String url;
					String photourl;
					
					tmp = (JSONObject)arrData.get(i);
					//제목 title
					title = (String) tmp.get("title");
					//내용 description
					content = (String) tmp.get("content");
					//url url
					url = (String) tmp.get("url");
					//사진 urlToImage
					photourl = (String) tmp.get("photourl");
					
					tmpArticles.title = title;
					tmpArticles.content = content;
					tmpArticles.url = url;
					tmpArticles.photourl = photourl;
					
					articles.add(tmpArticles);
				}
				return articles;
			} catch (Exception e){
				List<NewsArticles> articles = new ArrayList<>();
				return articles;
			}
		}
	
	
	// 뉴스 기사 스크랩 파일에 저장하기 (파일 읽기 + json에 추가하기)
	@SuppressWarnings("unchecked")
	public void saveScrapNews(NewsArticles newsArticle) {
		List<NewsArticles> scrapArticles = new ArrayList<>();
		// 파일읽기
		scrapArticles = getScrapArticles();
		// 리스트에 이미 동일 기사가 있으면 return
		for (int i = 0; i < scrapArticles.size(); i++) {
			if(scrapArticles.get(i).url.equals(newsArticle.url)) {
				System.out.println("이미 스크랩된 기사입니다.");
				return;
			}
		}
		scrapArticles.add(newsArticle);		
		JSONArray articlesJson = new JSONArray();
		
		for(int i = 0 ; i < scrapArticles.size(); i++) {
			JSONObject articleJson = new JSONObject();
			articleJson.put("title", scrapArticles.get(i).title);
			articleJson.put("content", scrapArticles.get(i).content);
			articleJson.put("url", scrapArticles.get(i).url);
			articleJson.put("photourl", scrapArticles.get(i).photourl);
			
			articlesJson.add(articleJson);
		}
		
		String jsonStr = articlesJson.toString();
		File jsonFile = new File("scrapArticles.json");
        writeStringToFile(jsonStr, jsonFile);
	}
	
	
	
	// str 파일에 저장하기
	public void writeStringToFile(String str, File file){
		try {
			//BufferedWriter writer = new BufferedWriter(new FileWriter(file));
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file.getPath()), "UTF8"));
	        writer.write(str);
	        writer.close();
		}catch(Exception e) {
			System.out.println("저장에 실패했습니다.");
		}
        
    }
	
	// 스크랩한 기사 가져오기
	public List<NewsArticles> getScrapArticles(){
		try{
			File file = new File("scrapArticles.json");
			FileInputStream fis = new FileInputStream(file);
			byte[] data = new byte[(int) file.length()];
			fis.read(data);
			fis.close();
			
			String readData = new String(data, "UTF-8");
			return parsingFileJson(readData);
			
						
        }catch (FileNotFoundException e) {
            // TODO: handle exception
        }catch(IOException e){
            System.out.println(e);
        }
		return new ArrayList<>();
	}
	
}
