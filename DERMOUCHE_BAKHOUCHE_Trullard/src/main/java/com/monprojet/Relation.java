package com.monprojet;

import com.monprojet.Colon;
import java.util.Objects;

public class Relation {
    private Colon c1;
    private Colon c2;

    public Relation(Colon c1, Colon c2) {
        if (c1.equals(c2)) {
            throw new IllegalArgumentException("Les deux colons dans une relation doivent être différents.");
        }
        this.c1 = c1;
        this.c2 = c2;
    }

    public Relation(Relation autre) {
        this.c1 = autre.getC1();
        this.c2 = autre.getC2();
    }

    public Colon getC1() {
        return c1;
    }

    public void setC1(Colon c1) {
        if (c1.equals(this.c2)) {
            throw new IllegalArgumentException("Les deux colons dans une relation doivent être différents.");
        }
        this.c1 = c1;
    }

    public Colon getC2() {
        return c2;
    }

    public void setC2(Colon c2) {
        if (c2.equals(this.c1)) {
            throw new IllegalArgumentException("Les deux colons dans une relation doivent être différents.");
        }
        this.c2 = c2;
    }

    public String getRelation(){
        return "Relation : " + this.c1.getNom() + " et " + this.c2.getNom();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Relation relation = (Relation) o;
        return (Objects.equals(c1, relation.c1) && Objects.equals(c2, relation.c2)) ||
                (Objects.equals(c1, relation.c2) && Objects.equals(c2, relation.c1));
    }

    @Override
    public int hashCode() {
        // L'ordre des colons ne compte pas, donc on additionne les hashcodes
        return Objects.hash(c1) + Objects.hash(c2);
    }

    @Override
    public String toString() {
        return "Relation{" +
                "c1=" + c1.getNom() +
                ", c2=" + c2.getNom() +
                '}';
    }
}
