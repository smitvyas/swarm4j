package org.swarm4J.swarms;


import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.swarm4J.core.AbstractParticle;
import org.swarm4J.core.MinimizerParticle;
import org.swarm4J.functions.ICostFunction;
import org.swarm4J.utils.MersenneTwisterFast;
import org.swarm4J.utils.Utils;


// <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
// #[regen=yes,id=DCE.F021341A-09E5-93D9-0E22-A6A355C8D0D1]
// </editor-fold> 
public class Swarm {

    public static final int MAXIMIZE = 1;
    public static final int MINIMIZE = 0;

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.BED390F0-AC1E-985D-30F6-4E5E89BFA116]
    // </editor-fold> 
    private List<AbstractParticle> particles;

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.08076557-A1B2-B4D6-04A6-52DA989003D3]
    // </editor-fold> 
    private boolean minimize;

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.43E29EB9-E800-58C4-3ABF-BCF62A24A906]
    // </editor-fold> 
    private List<Double> maxPositions;

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.D610E11E-605F-3354-3288-49AC707D6972]
    // </editor-fold> 
    private List<Double> minPositions;
    private int informantsPerParticle;
    private double optimizedValue;
    private List<Double> optimizedPosition;
    private final double phi;

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.14599890-4F8B-FE20-2986-2A53335432BC]
    // </editor-fold> 
    public Swarm (int numberOfParticles, int minimize, int dimensions, List<Double> maxPositions, 
            List<Double> minPositions, int informantsPerParticle, String functionClassName) {
        this.minimize = minimize == Swarm.MINIMIZE;
        this.maxPositions = maxPositions;
        this.minPositions = minPositions;
        this.informantsPerParticle = informantsPerParticle;
        this.init(numberOfParticles,dimensions, functionClassName);
        phi = 0;
    }

    public Swarm(int numberOfParticles, int minimize, int dimensions, List<Double> maxPositions,
            List<Double> minPositions, int informantsPerParticle, String functionClassName, double phi) {
        this.phi = phi;
        this.minimize = minimize == Swarm.MINIMIZE;
        this.maxPositions = maxPositions;
        this.minPositions = minPositions;
        this.informantsPerParticle = informantsPerParticle;
        this.init(numberOfParticles,dimensions, functionClassName);
    }
    public void init(int numberOfParticles, int dimensions, String functionClassName) {
        this.particles = new ArrayList<AbstractParticle>(numberOfParticles);
        MersenneTwisterFast mt = Utils.getMTInstance();
        double pos;
        double vel;
        if (this.minimize) {
            this.optimizedValue = Double.POSITIVE_INFINITY;
            for (int i = 0; i < numberOfParticles; i++) {
                MinimizerParticle p = null;
                if (this.phi == 0)
                    p = new MinimizerParticle(this,(ICostFunction)Utils.getInstanceByReflection(functionClassName),dimensions,this.informantsPerParticle);
                else
                    p = new MinimizerParticle(this,(ICostFunction)Utils.getInstanceByReflection(functionClassName),dimensions,this.informantsPerParticle,this.phi);
                List<Double> tempPosition = new ArrayList<Double>(dimensions);
                List<Double> tempVelocity = new ArrayList<Double>(dimensions);
                for (int j = 0; j < dimensions; j++) {
                    pos = this.minPositions.get(j) + (mt.nextDouble() * ((this.maxPositions.get(j) - this.minPositions.get(j)) + 1));
                    vel = (this.minPositions.get(j) - this.maxPositions.get(j))/2  + (mt.nextDouble() * (((this.maxPositions.get(j)
                            - this.minPositions.get(j))/2 - (this.minPositions.get(j) - this.maxPositions.get(j))/2) + 1));
                    tempPosition.add(pos);
                    tempVelocity.add(vel);
                }
                p.setPosition(tempPosition);
                p.setBestPosition(Utils.doubleListDeepCopy(tempPosition));
                p.setVelocity(tempVelocity);
                try {
                    p.evaluatePosition();
                } catch (Exception ex) {
                    System.out.println("particle"+i);
                }
                this.particles.add(p);

            }
        }
    }
    public void evolve() {
        for (AbstractParticle p : this.particles) {
            p.move();
        }
        for (AbstractParticle p : this.particles) {
            try {
                p.evaluatePosition();
            } catch (Exception ex) {
                ex.toString();
            }
        }
    }
    
    public List<Double> getOptimizedPosition() {
        //Double value  = this.particles.get(0).getOptimizedResult();
        final List<Double> position = new ArrayList<Double>();
        double x = 0;
        double y = 0;
        //Double[] position = null;
        //= (this.optimizedPosition == null)?this.particles.get(0).getBestPosition():this.optimizedPosition;
        if (this.minimize) {
            for (AbstractParticle p : this.particles) {
                if (p.getOptimizedResult() < this.optimizedValue) {
                    this.optimizedValue = p.getOptimizedResult();
                    position.clear();
                    System.out.println("cambio");
                    //position = p.getBestPosition().toArray(new Double[p.getBestPosition().size()]);
                    //position = new ArrayList<Double>();
                    for (Double v : p.getBestPosition()) {
                        String temp = v.toString();
                        position.add(Double.parseDouble(temp));
                    }
                    //position = p.getBestPosition().;
                }
            }
        }else{
            for (AbstractParticle p : this.particles) {
                if (p.getOptimizedResult() > this.optimizedValue) {
                    this.optimizedValue = p.getOptimizedResult();
                    //position = p.getBestPosition();
                }
            }
        }
        //System.out.println(this.optimizedValue);
        //System.out.println(position);
        if (position != null) {
            /*positionList = new ArrayList<Double>();
            for (Double d : position) {
            positionList.add(d);
            }*/
            //position.add(x);
            //position.add(y);
            this.optimizedPosition = position;
            return position;
        }
            
        return this.optimizedPosition;
    }

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,regenBody=yes,id=DCE.18765E00-B114-DB31-67EF-4882A8F6745C]
    // </editor-fold> 
    public List<Double> getMaxPositions () {
        return maxPositions;
    }

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,regenBody=yes,id=DCE.2A09A030-3590-44AD-4D8B-8CBBC960C4B9]
    // </editor-fold> 
    public void setMaxPositions (List<Double> val) {
        this.maxPositions = val;
    }

    public boolean isMinimize() {
        return minimize;
    }

    public void setMinimize(boolean minimize) {
        this.minimize = minimize;
    }

   

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,regenBody=yes,id=DCE.56EEF8E7-EAFE-B88D-53B5-AFA2876308AF]
    // </editor-fold> 
    public List<Double> getMinPositions () {
        return minPositions;
    }

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,regenBody=yes,id=DCE.4EBF3C34-EB04-5458-224A-4E5D77201FDC]
    // </editor-fold> 
    public void setMinPositions (List<Double> val) {
        this.minPositions = val;
    }

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,regenBody=yes,id=DCE.4FB22A4C-F751-944F-D47A-DB4820D49C65]
    // </editor-fold> 
    public List<AbstractParticle> getParticles () {
        return particles;
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append(System.getProperty("line.separator"));
        sb.append("***Swarm stats***");
        sb.append(System.getProperty("line.separator"));
        for(AbstractParticle p : this.particles) {
            sb.append("Particle: Result ");
            sb.append(p.getResult()+" Position");
            sb.append(p.getPosition());
            sb.append(System.getProperty("line.separator"));
        }
        sb.append("Best Position Overall: ");
        sb.append(this.getOptimizedPosition());
        sb.append(System.getProperty("line.separator"));
        sb.append("Best Value Overall: ");
        sb.append(this.optimizedValue);
        sb.append(System.getProperty("line.separator"));
        sb.append(System.getProperty("line.separator"));
        return sb.toString();
    }



}

