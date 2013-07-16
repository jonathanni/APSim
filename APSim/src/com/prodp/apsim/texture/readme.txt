Tips for creating textures

All textures appear in the order of its ID. i.e. fire comes first, then water. Null is not included.

The bottom-most 64x64 square is the base texture. All textures above it are for the faces of the material. They must have different shades.

If you can create a pixel grid for 64x64, it would make life much easier.

Suggested shading reading top down for 64x64 squares (Paint.NET settings, may be different elsewhere):

Top-most block

^   T  o  p    L  I G H  T    +40
|   Bot tom    D  A   R  K    -40
|   L e f t    SL GT LIGHT    +20
|   Ri g ht    SL GT DA RK    -20
|   B a c k    N O R M A L    +-0
|   F ron t    N O R M A L    +-0
    
Bottom-most block