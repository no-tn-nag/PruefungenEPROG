package aufgabe3;

public class Part implements Component {

	@Override
	public void process(Cost c) {
		// Ein generischer Part hat keine process() Semantik
	}

}


public class Fluegeltueren extends Part {

}

public class Fluxkompensator extends Part {
	
}

public class Schwebeumwandlung extends Part {
	
}

public class EinklappbareRaeder extends Part {
	
}

class OutatimeKennzeichen extends Part {
	
}

//erst relevant in Teil (b)
public class FirstEditionFluxkompensator extends Part {
	
}

//erst relevant in Teil (b)
class VerchromteRaeder extends Part {
	
}
