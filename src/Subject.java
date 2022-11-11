
public interface Subject {
	public void registerObserver(Observer O);
	public void removeObserver(Observer O);
	public void registerScrapObserver(Observer O);
	public void removeScrapObserver(Observer O);
	public void notifyObserver();
}
