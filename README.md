[![GrowtopiaBot](https://github.com/DrOreo002/GrowtopiaJavaBot/blob/master/JavaGT.png)](https://github.com/DrOreo002/GrowtopiaJavaBot)

# GrowtopiaJavaBot [Discontinued]
A simple growtopia bot, made with Java!

# Building
This project use maven, so you can build it directly using `mvn clean install` command!

# Usage
After building your jar will be placed inside the target folder or your build folder. And from there you can directly start it
also don't forget to change your bot data at https://github.com/DrOreo002/GrowtopiaJavaBot/blob/master/src/main/java/me/droreo002/bot/Launcher.java#L43

This project also require ENetJavaLib.dll, see my pinned repo in order to get one

Bot data example:
```json
{
    "botUsername": "YourUserName",
    "botPassword": "YourPassword",
    "enetLibPath": "lib\\ENetJavaLib.dll"
}
```
