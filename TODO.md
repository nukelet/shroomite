- Plan exceptions for the World interface (so that access/block addition/block removal at invalid positions can be handled with try/catch)
- **Important**: fix fire textures for WoodBlock
- Maybe adjust the rendering resolution to the screen size (having black bars and stuff) -- Viewports

- Create an `update` method in the block interface that allows the Physics components to access the derived block interfaces without explicit typecasting?
