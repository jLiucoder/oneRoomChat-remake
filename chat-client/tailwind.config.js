/** @type {import('tailwindcss').Config} */
module.exports = {
  content: ["./src/**/*.{js,jsx,ts,tsx}", "./public/index.html"],
  darkMode: true, // or 'media' or 'class'
  theme: {
    extend: {
      translate: {
        "minus-50": "-50%",
        "plus-50": "50%",
      },
      backgroundImage: {
        "backgrond-image": "url(../src/assets/background.png)",
      },
      maxHeight: {
        "chatList": "750px",
      },
      minHeight: {
        "chatList": "550px",
      },
    },
  },
  variants: {
    extend: {},
  },
  plugins: [],
  corePlugins: {
    preflight: false,
  },
};
