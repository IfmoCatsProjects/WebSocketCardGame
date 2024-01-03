const path = require("path");
module.exports = {
    watch: true,
    devtool: "source-map",
    entry: [
        './src/main/resources/static/js/start/forms.js',
        './src/main/resources/static/js/start/start.js',
        './src/main/resources/static/js/start/example.js',
        './src/main/resources/static/js/start/startUtils.js',

        // './src/main/resources/static/js/game/distribution.js',
        // './src/main/resources/static/js/game/cardManager.js',
        // './src/main/resources/static/js/game/events.js',
        // './src/main/resources/static/js/game/connection.js',

        './src/main/resources/static/js/app.js',
    ],
    output: {
        path: path.resolve(__dirname, 'src/main/resources/static/js/dist'),
        filename: "app.js"
    },
    module: {
        rules: [{
            test: /\.js$/,
            exclude: /node_modules/,
            use: {
                loader: "babel-loader",
            },
        },
            {
                test: /\.css$/,
                exclude: /node_modules/,
                use: ["style-loader", "css-loader"]
            }],
    },
}