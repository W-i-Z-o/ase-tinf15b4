package paradigms;

import java.awt.Point;
import java.util.Optional;
import java.util.UUID;

import api.Document;
import api.Document.Entry;
import api.Element;
import api.MouseEvent;
import api.Paradigm;

public class ListenerPattern implements Paradigm {

    private final DocumentListener documentListener;
    private Document document;
    private Element lastElement;
    private Point lastPoint;
    private UUID lastId;

    public ListenerPattern(Document initDoc,  DocumentListener documentListener) {
        this.document = initDoc;
        this.documentListener = documentListener;
    }

    public void mouseEvent(MouseEvent mouseEvent) {
    	switch (mouseEvent.type()){
    	case DOWN:
    		onMouseDown(mouseEvent);
    		break;
    	case UP:
    		onMouseUp();
    		break;
    	case MOVE:
    		onMouseMove(mouseEvent);
    	}
    }

    private void onMouseMove(MouseEvent mouseEvent) {
		if(lastElement!=null){
			int xOffset = mouseEvent.point().x - lastPoint.x;
			int yOffset = mouseEvent.point().y - lastPoint.y;
			Element newElement = lastElement.translate(new Point(xOffset, yOffset));
			Document newDocument = document.insert(lastId, newElement);
			this.document=newDocument;
			documentListener.documentUpdated(this.document);
		}
	}

	private void onMouseUp() {
		lastElement = null;
		lastPoint = null;
		lastId = null;
	}

	private void onMouseDown(MouseEvent mouseEvent) {		
		document.atPoint(mouseEvent.point()).ifPresent(e -> {
			lastElement = e.element();
			lastPoint = mouseEvent.point();
			lastId = e.id();
		});
	}

	public void dispose() {}
}
