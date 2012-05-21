package nz.ac.victoria.ecs.nwen304.project3.common.data;

import static org.junit.Assert.assertSame;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import nz.ac.victoria.ecs.nwen304.project3.data.DataExchange;
import nz.ac.victoria.ecs.nwen304.project3.entities.Container;
import nz.ac.victoria.ecs.nwen304.project3.entities.Item;
import nz.ac.victoria.ecs.nwen304.project3.entities.Note;

import org.junit.Test;

public class DataExchangePropertySetterTest {
	@Test
	public void testGetSingleItemWorks() {
		Item item = new DataExchange() {
			public void save(Item item) { /* NOP */}
			
			public Item getItemByID(UUID id) { return new Note("a", null, null); }
			
			public List<Item> getAllElementsInContainer(UUID containerID) { return null; }
			
			public void delete(Item item) { /* NOP */ }
		}.getItemByID(null);
		
		assertSame(true, item.isFromDatasource());
	}
	
	@Test
	public void testGettingListOfItemsWorks() {
		List<Item> items = new DataExchange() {
			@Override
			public void save(Item item) { /* NOP */ }
			
			@Override
			public Item getItemByID(UUID id) { return null; }
			
			@Override
			public List<Item> getAllElementsInContainer(UUID containerID) { 
				return Arrays.<Item>asList(new Note("a", null, null)); }
			
			@Override
			public void delete(Item item) { /* NOP */ }
		}.getAllElementsInContainer(null);
		
		for (Item i : items)
			assertSame(true, i.isFromDatasource());
	}
	
	@Test
	public void testGettingElementsInsideAContainerWorksCorreectly() {
		Item item = new DataExchange() {
			private Container c;
			
			{
				c = new Container("a", null, null);
				Note n = new Note("b", c, null);
				c.setItems(Arrays.<Item>asList(n));
			}
			
			public void save(Item item) { /* NOP */}
			
			public Item getItemByID(UUID id) { return c; }
			
			public List<Item> getAllElementsInContainer(UUID containerID) { return null; }
			
			public void delete(Item item) { /* NOP */ }
		}.getItemByID(null);
		
		assertSame(true, ((Container) item).getItems().get(0).isFromDatasource());
	}
}
