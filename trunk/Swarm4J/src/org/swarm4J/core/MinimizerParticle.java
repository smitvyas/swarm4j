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
    }

    @Override
    public void evaluatePosition() throws Exception{
         if (this.costFunction.evaluate(this.position) < this.optimizedResult) {
            this.bestPosition = this.position;
        }
    }

}
