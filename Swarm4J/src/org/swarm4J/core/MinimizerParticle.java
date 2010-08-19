/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.swarm4J.core;

import org.swarm4J.functions.ICostFunction;
import org.swarm4J.swarms.Swarm;

/**
 *
 * @author william
 */
public class MinimizerParticle extends AbstractParticle{

    public MinimizerParticle(Swarm swarm, ICostFunction costFunction, int dimensions,int numberOfInformants) {
        super(swarm,costFunction,dimensions,numberOfInformants);
        this.optimizedResult = Double.POSITIVE_INFINITY;
    }

     public MinimizerParticle(Swarm swarm, ICostFunction costFunction, int dimensions,int numberOfInformants, double phi) {
        super(swarm,costFunction,dimensions,numberOfInformants, phi);
        this.optimizedResult = Double.POSITIVE_INFINITY;
    }
    @Override
    public void evaluatePosition() throws Exception {
        double r = this.costFunction.evaluate(this.position);
        this.result = r;
        if (r < this.optimizedResult ) {
            this.bestPosition = this.position;
            this.optimizedResult = r;
        }
    }

    @Override
    protected void selectBestInformantPosition() {
        double best = this.informants.get(0).getResult();
        int index = 0;
        for(int i = 0; i < this.informants.size(); i++) {
            AbstractParticle p = this.informants.get(i);
            if (p.getResult() < best) {
                best = p.getResult();
                index = i;
            }
        }
        this.bestInformantPosition = this.informants.get(index).getPosition();
    }

}
