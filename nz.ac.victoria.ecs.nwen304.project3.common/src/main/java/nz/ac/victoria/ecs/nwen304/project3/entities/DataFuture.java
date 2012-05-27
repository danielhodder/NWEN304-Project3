package nz.ac.victoria.ecs.nwen304.project3.entities;

public interface DataFuture<T> {
	public T get();
	
	public static final class ImediateDataFuture<T> implements DataFuture<T> {

		private final T object;
		
		public ImediateDataFuture(final T object) {
			this.object = object;
		}

		@Override
		public T get() {
			return this.object;
		}
		
	}
}
