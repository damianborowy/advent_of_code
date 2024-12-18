using System;
using System.Collections;
using System.Collections.Generic;
using JetBrains.Annotations;
using UnityEngine;

public class Box : MonoBehaviour
{
    public Rigidbody2D rigidBody;
    public Solver solver;
    
    [CanBeNull] public Player player;

    private List<GameObject> GetMovableBoxes(Vector2 direction) => solver.doubleSizeMode
        ? GetDoubleWidthMovableBoxes(direction)
        : GetSingleWidthMovableBoxes(direction, distanceStep: 1);

    private List<GameObject> GetSingleWidthMovableBoxes(Vector2 direction, int distanceStep)
    {
        var hitObjects = Physics2D.RaycastAll(transform.position, direction);
        var movableBoxObjects = new List<GameObject>();
        int? distanceToBox = null;

        foreach (var hit in hitObjects)
        {
            if (!hit.collider.gameObject.CompareTag("Box")) continue;
            
            var currentDistance = Mathf.RoundToInt(hit.distance);

            if (distanceToBox is null || currentDistance == distanceToBox + distanceStep)
            {
                distanceToBox = currentDistance;
                movableBoxObjects.Add(hit.collider.gameObject);
            }
            else break;
        }

        return movableBoxObjects;
    }

    private List<GameObject> GetDoubleWidthMovableBoxes(Vector2 direction) => (direction.x, direction.y) switch
    {
        (-1f, 0) or (1f, 0) => GetHorizontalBoxes(direction),
        (0, 1f) or (0, -1f) => GetVerticalBoxes(direction),
        _ => throw new ArgumentOutOfRangeException(nameof(direction), direction, null)
    };

    private List<GameObject> GetHorizontalBoxes(Vector2 direction)
    {
        throw new NotImplementedException();
    }

    private List<GameObject> GetVerticalBoxes(Vector2 direction) =>
        GetSingleWidthMovableBoxes(direction, distanceStep: 2);
    
    private void OnCollisionEnter2D(Collision2D collision)
    {
        if (!collision.gameObject.CompareTag("Player") || player is null) return;
        var direction = player.lastPlayerMove;

        foreach (var box in GetMovableBoxes(direction))
        {
            box.transform.position = (Vector2)box.transform.position + direction;
        }
    }
}
