package com.designpattern.SOLID.InterfaceSegregationPrinciple;


import javax.crypto.Mac;

class Document{

}

interface Machine{
    void print (Document d);
    void fax (Document d);
    void scan (Document d);

}
interface Printer{
    void print(Document d);
}
interface Scanner{
    void scan(Document d);
}

interface FaxAndPrinter extends Fax,Scanner{

}
interface Fax{
    void fax(Document d);
}
class MultiFunctionPrinter implements Machine{
    @Override
    public void print(Document d) {

    }

    @Override
    public void fax(Document d) {

    }

    @Override
    public void scan(Document d) {

    }
}

class OldFashionedPrinter implements Machine{
    @Override
    public void print(Document d) {

    }

    @Override
    public void fax(Document d) {

    }

    @Override
    public void scan(Document d) {

    }
}

class JustAPrinter implements Printer{
    @Override
    public void print(Document d) {

    }
}

class Photocopier1 implements Printer, Scanner{
    @Override
    public void print(Document d) {

    }

    @Override
    public void scan(Document d) {

    }
}

class Photocopier2 implements FaxAndPrinter{
    @Override
    public void scan(Document d) {

    }

    @Override
    public void fax(Document d) {

    }
}

// Thay vì 1 interface lớn ta nên tách thành nhiều interface nhỏ với mục đích nhất định
//thay thì implement  interface Machine có nhiều methods thì nên thách thành các interface nhỏ hơn để tránh fai implement các method k cần thiết