package org.swarm4J.core;

import java.util.ArrayList;
import java.util.List;
import org.swarm4J.functions.ICostFunction;
import org.swarm4J.swarms.Swarm;
import org.swarm4J.utils.MersenneTwisterFast;
import org.swarm4J.utils.Utils;


// <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
// #[regen=yes,id=DCE.6B5CD210-9751-3916-2DB8-7BEAD22358F3]
// </editor-fold> 
public abstract class AbstractParticle {

    private int dimensions;

    protected double result;

    protected double optimizedResult;

    private Swarm swarm;

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.A82FFD68-DA8C-3320-D191-85146A0A25BC]
    // </editor-fold> 
    protected List<AbstractParticle> informants;

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.3B866CF8-FF0E-06B7-A0F5-D33D2BE0D38E]
    // </editor-fold> 
    private List<Double> velocity;

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.9F4F8DF8-D43D-6D02-512E-2BC272D330FB]
    // </editor-fold> 
    protected List<Double> position;

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.92445328-05DF-FAE4-DCE9-F3A00DAF82C9]
    // </editor-fold> 
    protected List<Double> bestPosition;

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.E880B3B0-A05B-6A3C-354C-5CDEE3FBC703]
    // </editor-fold> 
    protected List<Double> bestInformantPosition;

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.9775AFBF-F3A3-4B67-CDE7-A4EBEBF9E0F9]
    // </editor-fold> 
    protected ICostFunction costFunction;

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.EDF3EE84-A073-3560-99C2-8AAF20AA4418]
    // </editor-fold> 
    private double selfConfidence;

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.A825C29E-3181-35D5-1C29-83B320A08A49]
    // </editor-fold> 
    private double maxConfidence;
    private final int numberOfInformants;


    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.70317E14-6A5D-DFEF-7DB4-F2ACC948A619]
    // </editor-fold> 
    public AbstractParticle (Swarm swarm, ICostFunction costFunction, int dimensions, int numberOfInformants) {
        this.selfConfidence = 0.7;
        this.maxConfidence = 1.47;
        this.swarm = swarm;
        this.costFunction = costFunction;
        this.dimensions = dimensions;
        this.position = new ArrayList<Double>(dimensions);
        this.velocity = new ArrayList<Double>(dimensions);
        this.bestPosition = new ArrayList<Double>(dimensions);
        this.informants = new ArrayList<AbstractParticle>(numberOfInformants);
        this.numberOfInformants = numberOfInformants;
        //this.optimizedResult = Double.NaN;

    }
    public AbstractParticle(Swarm swarm, ICostFunction costFunction, int dimensions, int numberOfInformants, double phi) {
        this.selfConfidence = 1/(phi-1+Math.sqrt(phi*phi -2*phi));
        this.maxConfidence = phi*this.selfConfidence;
        this.swarm = swarm;
        this.costFunction = costFunction;
        this.dimensions = dimensions;
        this.position = new ArrayList<Double>(dimensions);
        this.velocity = new ArrayList<Double>(dimensions);
        this.bestPosition = new ArrayList<Double>(dimensions);
        this.informants = new ArrayList<AbstractParticle>(numberOfInformants);
        this.numberOfInformants = numberOfInformants;
    }

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,regenBody=yes,id=DCE.797F293A-4D17-F945-2561-66AAF6D69CB1]
    // </editor-fold> 
    public List<AbstractParticle> getInformants () {
        return informants;
    }

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,regenBody=yes,id=DCE.DF293F29-F583-E824-FC52-CA465EF7C78A]
    // </editor-fold> 
    public void setInformants (List<AbstractParticle> val) {
        this.informants = val;
    }

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,regenBody=yes,id=DCE.5F4DDC57-0B28-5762-1A2C-D571315B7FD2]
    // </editor-fold> 
    public List<Double> getBestInformantPosition () {
        return bestInformantPosition;
    }

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,regenBody=yes,id=DCE.8BCFE53D-11CE-DE81-0774-A62B20799F0F]
    // </editor-fold> 
    public List<Double> getBestPosition () {
        return bestPosition;
    }

    public void setBestPosition(List<Double> bestPosition) {
        this.bestPosition = bestPosition;
    }

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,regenBody=yes,id=DCE.36AAAF92-5126-5AAB-F7F5-54E0B14C7426]
    // </editor-fold> 
    public ICostFunction getCostFunction () {
        return costFunction;
    }

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,regenBody=yes,id=DCE.37A05E1A-1FA6-449F-46FF-B5985EE9E4ED]
    // </editor-fold> 
    public List<Double> getPosition () {
        return position;
    }

    public void setPosition(List<Double> position) {
        this.position = position;
    }

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,regenBody=yes,id=DCE.F5A52B97-BBD8-457F-96CD-72B66ABAB966]
    // </editor-fold> 
    public List<Double> getVelocity () {
        return velocity;
    }

    public void setVelocity(List<Double> velocity) {
        this.velocity = velocity;
    }

    public double getOptimizedResult() {
        return optimizedResult;
    }

    public double getResult() {
        return result;
    }

    
    protected abstract void selectBestInformantPosition();

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.E622AE27-621B-F0B1-FA8A-48589764685B]
    // </editor-fold> 
    public void move() {
        double newVelocity;
        double newPosition;
        this.selectInformants();
        this.selectBestInformantPosition();
        MersenneTwisterFast mt = Utils.getMTInstance();
        for (int i = 0; i < this.dimensions; i++) {
            newVelocity = selfConfidence*this.velocity.get(i) + this.maxConfidence*mt.nextDouble()*(this.bestPosition.get(i)-this.position.get(i)) +
                    this.maxConfidence*mt.nextDouble()*(this.bestInformantPosition.get(i)-this.position.get(i));
            newPosition = this.position.get(i) + newVelocity;
            //Check and confine particle if needed
            if (newPosition < this.swarm.getMinPositions().get(i)) {
               //this.velocity.set(i,0d);
               newVelocity = -newVelocity*0.5;
               newPosition = swarm.getMinPositions().get(i);
            }else{
                if (newPosition > this.swarm.getMaxPositions().get(i)) {
                    //this.velocity.set(i,0d);
                    newVelocity = -newVelocity*0.5;
                    newPosition = swarm.getMaxPositions().get(i);
                }
            }
            this.velocity.set(i,newVelocity);
            this.position.set(i, newPosition);
        }
        
    }


    
    public abstract void evaluatePosition() throws Exception;

    private void selectInformants() {
        int max = swarm.getParticles().size();
        int informantIndex;
        //ArrayList<Integer> pIndexes = new ArrayList<Integer>(this.informants.size());
        MersenneTwisterFast mt = Utils.getMTInstance();
        this.informants.clear();
        int i = 0;
        while (i < this.numberOfInformants) {
            informantIndex = mt.nextInt(max);
            //if (!pIndexes.contains(informantIndex)) {
                this.informants.add(swarm.getParticles().get(informantIndex));
                i++;
            //}
        }
    }

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,regenBody=yes,id=DCE.2BC6B3C7-D967-646F-5646-D1F562E40724]
    // </editor-fold> 
    public double getMaxConfidence () {
        return maxConfidence;
    }

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,regenBody=yes,id=DCE.97982996-5335-3059-ADED-C960DE3E6ACF]
    // </editor-fold> 
    public double getSelfConfidence () {
        return selfConfidence;
    }

}

