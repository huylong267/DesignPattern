package com.designpattern.SOLID.OpenClosePrinciple;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

enum COLOR {
    RED,GREEN,BLUE
}

enum SIZE {
    SMALL,MEDIUM,LARGE,YUGE
}

class Product{
    public String name;
    public COLOR color;
    public SIZE size;

    public Product(String name, COLOR color, SIZE size) {
        this.name = name;
        this.color = color;
        this.size = size;
    }

}
class ProductFilter {
    public Stream<Product> filterByColor(List<Product> products,COLOR color){
        return products.stream().filter(p -> p.color == color);
    }
    public Stream<Product> filterBySize(List<Product> products,SIZE size){
        return products.stream().filter(p -> p.size == size);
    }
    public Stream<Product> filterBySizeAndColor(List<Product> products,SIZE size,COLOR color){
        return products.stream().filter(p -> p.size == size && p.color == color);
    }

}

interface Specificattion<T>{
    boolean isSatisfied(T item);
}

interface Filter<T>{
    Stream<T> filter(List<T> items,Specificattion<T> spec);
}

class  ColorSpecification implements Specificattion<Product>{
    private COLOR color;

    public ColorSpecification(COLOR color) {
        this.color = color;
    }

    @Override
    public boolean isSatisfied(Product item) {
        return item.color  == color;
    }
}

class  SizeSpecification implements Specificattion<Product>{
    private SIZE size;

    public SizeSpecification(SIZE size) {
        this.size = size;
    }

    @Override
    public boolean isSatisfied(Product item) {
        return item.size  == size;
    }
}
class  BetterFilter implements Filter<Product>{
    @Override
    public Stream<Product> filter(List<Product> items, Specificattion<Product> spec) {
        return items.stream().filter(p -> spec.isSatisfied(p));
    }
}

class AndSpecification<T> implements Specificattion<T>{
    private Specificattion<T> fisrt, second;

    public AndSpecification(Specificattion<T> fisrt, Specificattion<T> second) {
        this.fisrt = fisrt;
        this.second = second;
    }

    @Override
    public boolean isSatisfied(T item) {
        return fisrt.isSatisfied(item) && second.isSatisfied(item);
    }
}
class Demo{
    public static void main(String[] args) {
        Product apple = new Product("Apple",COLOR.GREEN,SIZE.SMALL);
        Product tree = new Product("Tree",COLOR.GREEN,SIZE.LARGE);
        Product house = new Product("House",COLOR.BLUE,SIZE.LARGE);
        List<Product> products = Arrays.asList(apple,tree,house);
        ProductFilter productFilter = new ProductFilter();
        productFilter.filterByColor(products,COLOR.GREEN).forEach(p -> System.out.println(" - "+p.name+" is green"));

        BetterFilter betterFilter = new BetterFilter();
        System.out.println("Green product (new): ");
        betterFilter.filter(products,new ColorSpecification(COLOR.GREEN)).forEach(p -> System.out.println(" - "+p.name+" is green"));
        System.out.println("Large  blue product: ");
        betterFilter.filter(products,new AndSpecification<>(new ColorSpecification(COLOR.BLUE),new SizeSpecification(SIZE.LARGE)))
                .forEach(p -> System.out.println(" - "+p.name+" is large and blue"));
    }
    //Không nên sửa đổi trong class đã có sẵn , có thể mở rộng thêm = cách kế thừa .
    //Ở đây để lọc các thông số thì tạo các interface và implement để viết lại hàm lọc
}