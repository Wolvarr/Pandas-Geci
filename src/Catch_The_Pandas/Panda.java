package Catch_The_Pandas;

public class Panda extends Animal {

	protected Panda nextPanda;
	protected Animal previousAnimal;

	@Override
	public boolean move(Tile tileTo) {
		System.out.println("Called function panda.move()");
		if (location.getNeighbours().contains(tileTo)) {
			if (tileTo.getOnObject() == null) {
				location.movedFrom();
				if (this.nextPanda != null) {
					this.nextPanda.move(location);
				}
				tileTo.receive(this);
				return true;
			}

			else
				return tileTo.getOnObject().steppedOn(this);
		}

		return false;
	}

	public void grab(Panda p) {

		this.nextPanda = p;
		System.out.println("Called function panda.grab()");
	}

	// DOMIAN
	public void grabpreviousAnimal(Animal a) {

		this.previousAnimal = a;
		System.out.println("Called function panda.grabpreviousAnimal()");
	}

	public void release() {
		System.out.println("Called function panda.release()");
		this.previousAnimal = null;
		this.nextPanda = null;
	}

	// IDE KI KENE TALALNI VMIT
	@Override
	public boolean steppedOn(Animal Incoming) {

		System.out.println("Called function panda.steppedOn()");

		this.steppedOn((Orangutan) Incoming);

		return false;
	}

	// DOMIAN
	public boolean steppedOn(Orangutan o) {

		System.out.println("Called function panda.steppedOn(ORANGUTAN)");

		// check if o.grabbed==NULL
		if (o.getGrabbed() == null) {
			this.grabpreviousAnimal(o);
			o.grab(this);
		} else {
			o.getGrabbed().release();
			o.getGrabbed().grab(this);
			o.grab(this);

			this.grabpreviousAnimal(o);

		}

		return false;
	}

	// DOMIAN
	// EZ SEM JO IGY
	public boolean steppedOn(Panda p) {

		System.out.println("Called function panda.steppedOn(PANDA)");

		return false;
	}
	
	@Override
	public void fall()
    {
    	System.out.println("Called function panda.fall()");
    	if(nextPanda != null) {
    		release();
    	}
    }

}
