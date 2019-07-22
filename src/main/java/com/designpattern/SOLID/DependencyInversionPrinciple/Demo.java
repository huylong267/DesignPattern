package com.designpattern.SOLID.DependencyInversionPrinciple;



import org.javatuples.Triplet;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

enum Relationship {
    PARENT,CHILD,SIBLING
}

class Person {
    public String name;

    public Person(String name) {
        this.name = name;
    }
}
class Relationships implements RelationshipBrowser{ //low level
    private List<Triplet<Person,Relationship,Person>> relations = new ArrayList<>();
    public void addParentAndChild(Person parent,Person child){
        relations.add(new Triplet<>(parent,Relationship.PARENT,child));
        relations.add(new Triplet<>(child,Relationship.CHILD,parent));
    }

    public List<Triplet<Person, Relationship, Person>> getRelations() {
        return relations;
    }

    @Override
    public List<Person> findAllChirldrenOf(String name) {
        return  relations.stream().filter(x -> Objects.equals(x.getValue0().name,name)&& x.getValue1() == Relationship.PARENT)
                .map(Triplet::getValue2).collect(Collectors.toList());
    }
}

class Research{ // high level
//    public Research(Relationships relationships){
//        List<Triplet<Person,Relationship,Person>> relations = relationships.getRelations();
//        relations.stream().filter(x -> x.getValue0().name.equals("John")&& x.getValue1() == Relationship.PARENT)
//                .forEach(ch -> System.out.println("John has a child called " +ch.getValue2().name));
//    }

    public Research(RelationshipBrowser relationshipBrowser){
        List<Person> chirldren = relationshipBrowser.findAllChirldrenOf("John");
        for(Person person : chirldren){
            System.out.println("John has a child called "+person.name);
        }
    }
}

interface RelationshipBrowser{
    List<Person> findAllChirldrenOf(String name);
}

class Demo{
    public static void main(String[] args) {
        Person parent = new Person("John");
        Person child1 = new Person("Chris");
        Person child2 = new Person("Matt");
        Relationships relationships = new Relationships();
        relationships.addParentAndChild(parent,child1);
        relationships.addParentAndChild(parent,child2);
        Research research = new Research(relationships);
    }
}

//Các module cấp cao không nên phụ thuộc vào các module cấp thấp. Cả hai nên phụ thuộc vào abstraction.
//Abstraction không nên phụ thuộc vào detail. Detail nên phụ thuộc vào abstraction.
//thay vì class Research phụ thuộc vào hàm  relationships.getRelations() của  class  Relationships  thì nên để
// cả 2 class Reseach  và Relationships đều phụ thuộc vào interface RelationshipBrowser