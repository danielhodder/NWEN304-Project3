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
		final Item item = new DataExchange() {
			@Override
			public void save(final Item item) { /* NOP */}
			
			@Override
			public Item getItemByID(final UUID id) { return new Note("a", null, null); }
			
			@Override
			public List<Item> getAllElementsInContainer(final UUID containerID) { return null; }
			
			@Override
			public void delete(final Item item) { /* NOP */ }

			@Override
			public Container getRootContainer() { return null; }
		}.getItemByID(null);
		
		assertSame(true, item.isFromDatasource());
	}
	
	@Test
	public void testGettingListOfItemsWorks() {
		final List<Item> items = new DataExchange() {
			@Override
			public void save(final Item item) { /* NOP */ }
			
			@Override
			public Item getItemByID(final UUID id) { return null; }
			
			@Override
			public List<Item> getAllElementsInContainer(final UUID containerID) { 
				return Arrays.<Item>asList(new Note("a", null, null)); }
			
			@Override
			public void delete(final Item item) { /* NOP */ }
			
			@Override
			public Container getRootContainer() { return null; }
		}.getAllElementsInContainer((UUID) null);
		
		for (final Item i : items)
			assertSame(true, i.isFromDatasource());
	}
	
	@Test
	public void testGettingElementsInsideAContainerWorksCorreectly() {
		final Item item = new DataExchange() {
			private Container c;
			
			{
				this.c = new Container("a");
				final Note n = new Note("b", this.c, null);
				this.c.setItems(Arrays.<Item>asList(n));
			}
			
			@Override
			public void save(final Item item) { /* NOP */}
			
			@Override
			public Item getItemByID(final UUID id) { return this.c; }
			
			@Override
			public List<Item> getAllElementsInContainer(final UUID containerID) { return null; }
			
			@Override
			public void delete(final Item item) { /* NOP */ }
			
			@Override
			public Container getRootContainer() { return null; }
		}.getItemByID(null);
		
		assertSame(true, ((Container) item).getItems().get(0).isFromDatasource());
	}
}
