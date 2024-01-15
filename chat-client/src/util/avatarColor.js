export default function AvatarColor(str) {
    let hash = 0;
    for (let i = 0; i < str.length; i++) {
        // Using a fixed number for each character to ensure consistency
        hash = (hash + str.charCodeAt(i) * (i + 1)) % 360;
    }

    // Using HSL for color representation
    return `hsl(${hash}, 70%, 50%)`;
}