```mermaid
classDiagram
    class Ponto {
        +x
        +y
        +cor
        +desenhar()
    }

    class D2 {
        -coordenada
        -estrutura
        +area()
        +perimetro()
        +desenhar()
    }

    class D3 {
        -profundidade
        +area()
        +volume()
        +perimetro()
        +desenhar()
    }

    class Reta {
        +x1
        +y1
        +desenhar()
    }

    class Circulo {
        +raio
        +area()
        +perimetro()
        +desenhar()
    }

    class Retangulo {
        +base
        +largura
        +area()
        +perimetro()
        +desenhar()
    }

    class Cilindro {
        +raio
        +area()
        +volume()
        +perimetro()
        +desenhar()
    }

    class Piramide {
        +base
        +largura
        +area()
        +volume()
        +perimetro()
        +desenhar()
    }

    Ponto <|-- D2
    Ponto <|-- D3
      Ponto <|-- Reta
    D2 <|-- Circulo
    D2 <|-- Retangulo
    D3 <|-- Cilindro
    D3 <|-- Piramide
