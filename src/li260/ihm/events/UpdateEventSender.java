package li260.ihm.events;

public interface UpdateEventSender {
	public void add(UpdateEventListener listener);

	public void update();
}